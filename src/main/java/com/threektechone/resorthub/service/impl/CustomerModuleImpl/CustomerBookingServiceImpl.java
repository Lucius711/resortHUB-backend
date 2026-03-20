package com.threektechone.resorthub.service.impl.CustomerModuleImpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.threektechone.resorthub.common.exception.custom.ResourceNotFoundException;
import com.threektechone.resorthub.dto.CustomerModuleDTO.BookingRequestDTO;
import com.threektechone.resorthub.dto.CustomerModuleDTO.CustomerBookingDetailResponseDTO;
import com.threektechone.resorthub.dto.CustomerModuleDTO.CustomerBookingListResponseDTO;
import com.threektechone.resorthub.enums.BookingStatus;
import com.threektechone.resorthub.helper.BookingHelper.BookingCodeGenerator;
import com.threektechone.resorthub.mapper.BookingMapper;
import com.threektechone.resorthub.models.Booking;
import com.threektechone.resorthub.models.BookingMeal;
import com.threektechone.resorthub.models.Resort;
import com.threektechone.resorthub.models.User;
import com.threektechone.resorthub.common.exception.custom.InvalidBookingStatusException;
import com.threektechone.resorthub.policy.booking.CancellationPolicy;
import com.threektechone.resorthub.policy.booking.CapacityPolicy;
import com.threektechone.resorthub.repositories.BookingRepository;
import com.threektechone.resorthub.repositories.ResortRepository;
import com.threektechone.resorthub.repositories.UserRepository;
import com.threektechone.resorthub.service.CommonModule.BookingPriceCalculator;
import com.threektechone.resorthub.service.CustomerModule.CustomerBookingMealService;
import com.threektechone.resorthub.service.CustomerModule.CustomerBookingService;

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

    @Override
    public void createBooking(BookingRequestDTO dto,String email,int resortId) {
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
    public Page<CustomerBookingListResponseDTO> getCustomerBookings(String email, String searchkey, BookingStatus status, Pageable pageable) {
        Page<Booking> bookingList = bookingRepository.getCustomerBookings(email, searchkey, status, pageable);

        return bookingList.map(bookingMapper::toCustomerBookingListResponseDTO);
    }

    @Override
    public CustomerBookingDetailResponseDTO getCustomerBookingDetail(int bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
        .orElseThrow(() -> new ResourceNotFoundException("Booking not found!"));

        CustomerBookingDetailResponseDTO dto = bookingMapper.toCustomerBookingDetailResponseDTO(booking);
        dto.setMealPrice(bookingPriceCalculator.calculateMealCost(dto.getMeals()));

        return dto;
    }

    @Override
    public void cancelExpiredBookings() {
        List<Booking> expiredBookings = bookingRepository.findExpiredBookings(LocalDateTime.now());

        expiredBookings.forEach(b -> b.setStatus(BookingStatus.CANCELED));

        expiredBookings.forEach(b -> b.setCanceledAt(LocalDateTime.now()));

        bookingRepository.saveAll(expiredBookings);
    }

    @Override
    public void cancelBookingByCustomer(int bookingId, String customerMail) {
        Booking booking = bookingRepository.findById(bookingId)
        .orElseThrow(() -> new ResourceNotFoundException("Booking not found!"));

        cancellationPolicy.validateCancel(booking, customerMail);

        booking.setStatus(BookingStatus.CANCELED);
        booking.setCanceledAt(LocalDateTime.now());
        bookingRepository.save(booking);
    }
    
}
