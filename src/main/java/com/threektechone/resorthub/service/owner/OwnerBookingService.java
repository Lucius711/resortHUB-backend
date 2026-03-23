package com.threektechone.resorthub.service.owner;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.threektechone.resorthub.dto.owner.BookingRequestDecisionDTO;
import com.threektechone.resorthub.dto.owner.OwnerBookingDetailResponseDTO;
import com.threektechone.resorthub.dto.owner.OwnerBookingListResponseDTO;
import com.threektechone.resorthub.enums.BookingStatus;

public interface OwnerBookingService {
    Page<OwnerBookingListResponseDTO> getOwnerBookings(String email,String searchkey,BookingStatus status,Pageable pageable);

    OwnerBookingDetailResponseDTO getOwnerBookingDetail(int bookingId, String ownerEmail);

    void reviewBooking(BookingRequestDecisionDTO dto, int bookingId, String ownerEmail);

    void checkIn(int bookingId, String ownerEmail);

    void checkOut(int bookingId, String ownerEmail);
}
