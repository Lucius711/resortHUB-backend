package com.threektechone.resorthub.service.OwnerModule;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.threektechone.resorthub.dto.OwnerModuleDTO.EditRequestDTO;
import com.threektechone.resorthub.dto.OwnerModuleDTO.OwnerResortsResponseDTO;
import com.threektechone.resorthub.dto.OwnerModuleDTO.RegisterAmenitiesRequestDTO;
import com.threektechone.resorthub.dto.OwnerModuleDTO.RegisterBasicInfoRequestDTO;
import com.threektechone.resorthub.dto.OwnerModuleDTO.RegisterCapacityPricingRequestDTO;
import com.threektechone.resorthub.dto.OwnerModuleDTO.RegisterRequestDTO;
import com.threektechone.resorthub.enums.ResortStatus;

public interface ResortService {
    Page<OwnerResortsResponseDTO> getAllOwnerResorts(String email,String searchkey,ResortStatus status,Pageable pageable);
    void createEditRequest(EditRequestDTO dto,String email);
    int createRegistrationResort(RegisterRequestDTO dto,String email);
    void updateBasicInfoResort(RegisterBasicInfoRequestDTO dto,int resortId);
    void updateCapacityPriceResort(RegisterCapacityPricingRequestDTO dto,int resortId);
    void updateAmenitiesResort(RegisterAmenitiesRequestDTO dtom,int resortId);
}
