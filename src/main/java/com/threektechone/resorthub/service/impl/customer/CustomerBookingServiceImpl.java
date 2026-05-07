package com.threektechone.resorthub.service.impl.customer;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.threektechone.resorthub.common.exception.custom.InvalidBookingStatusException;
import com.threektechone.resorthub.common.exception.custom.ResourceNotFoundException;
import com.threektechone.resorthub.common.exception.custom.UnauthorizedException;
import com.threektechone.resorthub.dto.customer.BookingCreatedResponseDTO;
import com.threektechone.resorthub.dto.customer.BookingRequestDTO;
import com.threektechone.resorthub.dto.customer.CustomerBookingDetailResponseDTO;
import com.threektechone.resorthub.dto.customer.CustomerBookingListResponseDTO;
import com.threektechone.resorthub.enums.BookingStatus;
import com.threektechone.resorthub.enums.PaymentGatewayProvider;
import com.threektechone.resorthub.enums.PaymentStatus;
import com.threektechone.resorthub.helper.booking.BookingCodeGenerator;
import com.threektechone.resorthub.mapper.booking.BookingMapper;
import com.threektechone.resorthub.models.Booking;
import com.threektechone.resorthub.models.BookingMeal;
import com.threektechone.resorthub.models.Payment;
import com.threektechone.resorthub.models.Resort;
import com.threektechone.resorthub.models.User;
import com.threektechone.resorthub.policy.booking.CancellationPolicy;
import com.threektechone.resorthub.policy.booking.CapacityPolicy;
import com.threektechone.resorthub.repositories.BookingRepository;
import com.threektechone.resorthub.repositories.PaymentRepository;
import com.threektechone.resorthub.repositories.ResortRepository;
import com.threektechone.resorthub.repositories.UserRepository;
import com.threektechone.resorthub.service.common.BookingPriceCalculator;
import com.threektechone.resorthub.service.customer.CustomerBookingMealService;
import com.threektechone.resorthub.service.customer.CustomerBookingService;
import com.threektechone.resorthub.strategy.booking.StripeBookingPaymentProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerBookingServiceImpl implements CustomerBookingService {

    private final ResortRepository resortRepository;
    private final UserRepository userRepository;
    private final BookingMapper bookingMapper;
    private final BookingRepository bookingRepository;
    private final CustomerBookingMealService bookingMealService;
    private final BookingPriceCalculator bookingPriceCalculator;
    private final CapacityPolicy capacityPolicy;
    private final CancellationPolicy cancellationPolicy;
    private final PaymentRepository paymentRepository;
    private final StripeBookingPaymentProvider stripeBookingPaymentProvider;

    @Override
    @Transactional
    public void createBooking(BookingRequestDTO dto, String email, int resortId) {
        if (dto == null) {
            throw new InvalidBookingStatusException("Booking request is required");
        }
        if (dto.getCheckInDate() == null || dto.getCheckOutDate() == null) {
            throw new InvalidBookingStatusException("Check-in/check-out dates are required");
        }
        if (!dto.getCheckOutDate().isAfter(dto.getCheckInDate())) {
            throw new InvalidBookingStatusException("Check-out date must be after check-in date");
        }
        if (dto.getNumberOfPerson() <= 0) {
            throw new InvalidBookingStatusException("Number of person must be positive");
        }
        if (dto.getMeals() == null || dto.getMeals().isEmpty()) {
            throw new InvalidBookingStatusException("Meals are required");
        }

        dto.getMeals().forEach(meal -> {
            if (meal == null) {
                throw new InvalidBookingStatusException("Meal is required");
            }
            if (meal.getMenuId() <= 0) {
                throw new InvalidBookingStatusException("Invalid menu id");
            }
            if (meal.getQuantity() <= 0) {
                throw new InvalidBookingStatusException("Invalid meal quantity");
            }
        });

        Resort resort = resortRepository.findById(resortId)
                .orElseThrow(() -> new ResourceNotFoundException("Resort not found!"));

        User customer = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        capacityPolicy.validateCapacity(dto.getNumberOfPerson(), resort.getMaxGuest());

        Booking booking = bookingMapper.toBooking(dto);
        booking.setBookingCode(BookingCodeGenerator.generateBookingCode());
        booking.setCustomer(customer);
        booking.setResort(resort);
        booking.setStatus(BookingStatus.PENDING);

        List<BookingMeal> meals = dto.getMeals()
                .stream()
                .map(meal -> bookingMealService.mapToBookingMeal(meal, booking))
                .toList();

        booking.setMeals(meals);
        booking.setTotalPrice(bookingPriceCalculator.calculateTotalPrice(resort, dto));
        bookingRepository.save(booking);
    }

    @Override
    public Page<CustomerBookingListResponseDTO> getCustomerBookings(String email, String searchkey,
            BookingStatus status, Pageable pageable) {
        Page<Booking> bookingList = bookingRepository.getCustomerBookings(email, searchkey, status, pageable);

        return bookingList.map(bookingMapper::toCustomerBookingListResponseDTO);
    }

    @Override
    @Cacheable(cacheNames = "customer-booking-detail", key = "#bookingId", unless = "#result == null")
    public CustomerBookingDetailResponseDTO getCustomerBookingDetail(int bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found!"));

        CustomerBookingDetailResponseDTO dto = bookingMapper.toCustomerBookingDetailResponseDTO(booking);
        dto.setMealPrice(bookingPriceCalculator.calculateMealCost(dto.getMeals()));

        return dto;
    }

    @Override
    @CacheEvict(cacheNames = "customer-booking-detail", allEntries = true)
    public void cancelExpiredBookings() {
        List<Booking> expiredBookings = bookingRepository.findExpiredBookings(LocalDateTime.now());

        expiredBookings.forEach(b -> b.setStatus(BookingStatus.CANCELED));

        expiredBookings.forEach(b -> b.setCanceledAt(LocalDateTime.now()));

        bookingRepository.saveAll(expiredBookings);
    }

    @Override
    @CacheEvict(cacheNames = "customer-booking-detail", key = "#bookingId")
    public void cancelBookingByCustomer(int bookingId, String customerMail) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found!"));

        cancellationPolicy.validateCancel(booking, customerMail);

        booking.setStatus(BookingStatus.CANCELED);
        booking.setCanceledAt(LocalDateTime.now());
        bookingRepository.save(booking);
    }

    @Override
    @CacheEvict(cacheNames = "customer-booking-detail", key = "#bookingId")
    @Transactional
    public BookingCreatedResponseDTO payBooking(int bookingId, String customerEmail, String clientIp) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found!"));

        if (!booking.getCustomer().getEmail().equalsIgnoreCase(customerEmail)) {
            throw new UnauthorizedException("You dont have permission!");
        }
        if (booking.getStatus() != BookingStatus.APPROVED) {
            throw new InvalidBookingStatusException("Payment is only available after the owner approves the booking");
        }

        Payment payment = booking.getPayment();
        if (payment != null && payment.getPaymentStatus() == PaymentStatus.SUCCESS) {
            throw new InvalidBookingStatusException("Booking is already paid");
        }

        if (payment == null) {
            payment = Payment.builder()
                    .booking(booking)
                    .amount(booking.getTotalPrice())
                    .paymentMethod("GATEWAY_PENDING")
                    .paymentStatus(PaymentStatus.PENDING)
                    .build();
            paymentRepository.save(payment);
            paymentRepository.flush();
        } else {
            payment.setAmount(booking.getTotalPrice());
            payment.setPaymentMethod("GATEWAY_PENDING");
            payment.setPaymentStatus(PaymentStatus.PENDING);
            paymentRepository.save(payment);
            paymentRepository.flush();
        }

        payment.setGatewayProvider(PaymentGatewayProvider.STRIPE);

        String paymentUrl = stripeBookingPaymentProvider.createCheckoutUrl(payment, booking, clientIp);
        paymentRepository.save(payment);

        booking.setPayment(payment);
        bookingRepository.save(booking);

        return BookingCreatedResponseDTO.builder()
                .bookingId(booking.getBookingId())
                .bookingCode(booking.getBookingCode())
                .totalAmount(booking.getTotalPrice())
                .paymentProvider(PaymentGatewayProvider.STRIPE)
                .paymentStatus(payment.getPaymentStatus())
                .paymentUrl(paymentUrl)
                .build();
    }

}
