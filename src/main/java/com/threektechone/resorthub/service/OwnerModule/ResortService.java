package com.threektechone.resorthub.service.OwnerModule;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.threektechone.resorthub.dto.OwnerModuleDTO.OwnerResortsResponseDTO;
import com.threektechone.resorthub.enums.ResortStatus;

public interface ResortService {
    Page<OwnerResortsResponseDTO> getAllOwnerResorts(String email,String searchkey,ResortStatus status,Pageable pageable);
}
