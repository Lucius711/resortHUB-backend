package com.threektechone.resorthub.service.OwnerModule;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.threektechone.resorthub.dto.OwnerModuleDTO.OwnerBookingDetailResponseDTO;
import com.threektechone.resorthub.dto.OwnerModuleDTO.OwnerBookingListResponseDTO;
import com.threektechone.resorthub.enums.BookingStatus;

public interface OwnerBookingService {
    Page<OwnerBookingListResponseDTO> getOwnerBookings(String email,String searchkey,BookingStatus status,Pageable pageable);

    OwnerBookingDetailResponseDTO getOwnerBookingDetail(int bookingId);
}
