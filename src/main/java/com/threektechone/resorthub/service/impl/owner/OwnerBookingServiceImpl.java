package com.threektechone.resorthub.service.impl.owner;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.threektechone.resorthub.common.exception.custom.CheckInNotAllowedException;
import com.threektechone.resorthub.common.exception.custom.CheckOutNotAllowedException;
import com.threektechone.resorthub.common.exception.custom.RequestAlreadyReviewedException;
import com.threektechone.resorthub.common.exception.custom.ResourceNotFoundException;
import com.threektechone.resorthub.common.exception.custom.UnauthorizedException;
import com.threektechone.resorthub.dto.owner.BookingRequestDecisionDTO;
import com.threektechone.resorthub.dto.owner.OwnerBookingDetailResponseDTO;
import com.threektechone.resorthub.dto.owner.OwnerBookingListResponseDTO;
import com.threektechone.resorthub.enums.BookingStatus;
import com.threektechone.resorthub.enums.ReviewAction;
import com.threektechone.resorthub.mapper.booking.BookingMapper;
import com.threektechone.resorthub.models.Booking;
import com.threektechone.resorthub.policy.booking.BookingTimePolicy;
import com.threektechone.resorthub.policy.booking.CheckInPolicy;
import com.threektechone.resorthub.policy.booking.CheckOutPolicy;
import com.threektechone.resorthub.repositories.BookingRepository;
import com.threektechone.resorthub.service.common.BookingPriceCalculator;
import com.threektechone.resorthub.service.owner.OwnerBookingService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class OwnerBookingServiceImpl implements OwnerBookingService {
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final BookingPriceCalculator bookingPriceCalculator;
    private final CheckInPolicy checkInPolicy;
    private final CheckOutPolicy checkOutPolicy;
    private final BookingTimePolicy bookingTimePolicy;

    private void ensureOwnerAccess(Booking booking, String ownerEmail) {
        if (booking == null || booking.getResort() == null || booking.getResort().getOwner() == null
                || booking.getResort().getOwner().getEmail() == null
                || !booking.getResort().getOwner().getEmail().equalsIgnoreCase(ownerEmail)) {
            throw new UnauthorizedException("You dont have permission!");
        }
    }

    private Boolean canCheckIn(Booking booking) {
        Boolean occupied = bookingRepository.isRoomOccupied(booking.getResort().getResortId());

        Boolean roomAvailable = !occupied;

        Boolean checkInTimeValid = bookingTimePolicy.isCheckInTimeValid(booking);

        return checkInPolicy.canCheckIn(booking, roomAvailable,checkInTimeValid);
    }

    private Boolean canCheckOut(Booking booking) {
        Boolean checkOutTimeValid = bookingTimePolicy.isCheckOutTimeValid(booking);

        return checkOutPolicy.canCheckOut(booking, checkOutTimeValid);
    }

    @Override
    public Page<OwnerBookingListResponseDTO> getOwnerBookings(String email, String searchkey, BookingStatus status, Pageable pageable) {
        Page<Booking> bookingList = bookingRepository.getOwnerBookings(email, searchkey, status, pageable);

        return bookingList.map(bookingMapper::toOwnerBookingListResponseDTO);
    }

    @Override
    public OwnerBookingDetailResponseDTO getOwnerBookingDetail(int bookingId, String ownerEmail) {
        Booking booking = bookingRepository.findById(bookingId)
        .orElseThrow(() -> new ResourceNotFoundException("Booking not found!"));

        ensureOwnerAccess(booking, ownerEmail);

        Boolean occupied = bookingRepository.isRoomOccupied(booking.getResort().getResortId());

        Boolean roomAvailable = !occupied;

        Boolean canCheckIn = canCheckIn(booking);
        
        Boolean canCheckOut =canCheckOut(booking);

        OwnerBookingDetailResponseDTO dto = bookingMapper.toOwnerBookingDetailResponseDTO(booking);
        dto.setMealPrice(bookingPriceCalculator.calculateMealCost(dto.getMeals()));
        dto.setRoomAvailable(roomAvailable);
        dto.setCanCheckIn(canCheckIn);
        dto.setCanCheckOut(canCheckOut);
        return dto;
    }

    @Override
    public void reviewBooking(BookingRequestDecisionDTO dto, int bookingId, String ownerEmail) {
        Booking booking = bookingRepository.findById(bookingId)
        .orElseThrow(() -> new ResourceNotFoundException("Booking not found!"));

        ensureOwnerAccess(booking, ownerEmail);

        if (booking.getStatus() != BookingStatus.PENDING) {
           throw new RequestAlreadyReviewedException("Request already reviewed");
        }

        if (dto.getAction() == ReviewAction.APPROVE) {
            booking.setStatus(BookingStatus.APPROVED);
        }
        else if (dto.getAction() == ReviewAction.REJECT) {
            booking.setStatus(BookingStatus.REJECTED);
            booking.setReason(dto.getReason());
        }      
        bookingRepository.save(booking);
    }

    @Override
    public void checkIn(int bookingId, String ownerEmail) {
        Booking booking = bookingRepository.findById(bookingId)
        .orElseThrow(() -> new ResourceNotFoundException("Booking not found!"));

        ensureOwnerAccess(booking, ownerEmail);

        Boolean canCheckIn = canCheckIn(booking);

        if (!canCheckIn) {
            throw new CheckInNotAllowedException("Can not check in!");
        }
        booking.setStatus(BookingStatus.CHECKED_IN);
        bookingRepository.save(booking);
    }

    @Override
    public void checkOut(int bookingId, String ownerEmail) {
        Booking booking = bookingRepository.findById(bookingId)
        .orElseThrow(() -> new ResourceNotFoundException("Booking not found!"));

        ensureOwnerAccess(booking, ownerEmail);

        Boolean canCheckOut = canCheckOut(booking);

        if (!canCheckOut) {
            throw new CheckOutNotAllowedException("Can not check out!");
        }
        booking.setStatus(BookingStatus.COMPLETED);
        bookingRepository.save(booking);
    }


    
}
