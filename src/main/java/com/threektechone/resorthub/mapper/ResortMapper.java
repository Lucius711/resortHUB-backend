package com.threektechone.resorthub.mapper;

import org.springframework.stereotype.Component;

import com.threektechone.resorthub.dto.OwnerModuleDTO.OwnerResortsResponseDTO;
import com.threektechone.resorthub.enums.ContractStatus;
import com.threektechone.resorthub.models.Resort;

@Component
public class ResortMapper {

    public OwnerResortsResponseDTO toOwnerResortList(Resort resort) {
        OwnerResortsResponseDTO dto = new OwnerResortsResponseDTO();
        dto.setResortId(resort.getResortId());
        dto.setResortName(resort.getName());
        
        String approvedByName = resort.getContracts()
            .stream()
            .filter(c -> c.getStatus() == ContractStatus.ACTIVE)
            .findFirst()
            .map(c -> c.getStaff().getFullName())
            .orElse(null);

        dto.setApprovedByName(approvedByName);

        String approvedByPhone = resort.getContracts()
            .stream()
            .filter(c -> c.getStatus() == ContractStatus.ACTIVE)
            .findFirst()
            .map(c -> c.getStaff().getPhone())
            .orElse(null);
        
        dto.setApprovedByPhone(approvedByPhone);
        dto.setResortStatus(resort.getStatus());
        dto.setLocation(resort.getLocation());
        dto.setCreatedAt(resort.getCreatedAt());
        return dto;
    }
    
}
