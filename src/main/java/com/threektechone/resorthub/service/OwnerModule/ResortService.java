package com.threektechone.resorthub.service.OwnerModule;

import org.springframework.data.domain.Pageable;

import com.threektechone.resorthub.dto.OwnerModuleDTO.OwnerResortResponseDTO;
import com.threektechone.resorthub.enums.BookingStatus;

public interface ResortService {
    OwnerResortResponseDTO getAllOwnerResorts(String searchkey,BookingStatus bookingStatus,Pageable pageable);
}
