package com.threektechone.resorthub.mapper;

import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Component;

import com.threektechone.resorthub.ExceptionHandler.CustomException.ResourceNotFoundException;
import com.threektechone.resorthub.dto.OwnerModuleDTO.OwnerResortsResponseDTO;
import com.threektechone.resorthub.dto.OwnerModuleDTO.RegisterAmenitiesRequestDTO;
import com.threektechone.resorthub.dto.OwnerModuleDTO.RegisterBasicInfoRequestDTO;
import com.threektechone.resorthub.dto.OwnerModuleDTO.RegisterCapacityPricingRequestDTO;
import com.threektechone.resorthub.dto.OwnerModuleDTO.RegisterRequestDTO;
import com.threektechone.resorthub.enums.ContractStatus;
import com.threektechone.resorthub.enums.ResortStatus;
import com.threektechone.resorthub.models.Resort;
import com.threektechone.resorthub.models.ResortAmenity;
import com.threektechone.resorthub.repositories.ResortAmenityRepository;
import com.threektechone.resorthub.repositories.ResortRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ResortMapper {

    private final ResortRepository resortRepository;
    private final ResortAmenityRepository resortAmenityRepository;

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
        dto.setCity(resort.getCity());
        dto.setDistrict(resort.getDistrict());
        dto.setAddress(resort.getAddress());
        dto.setCreatedAt(resort.getCreatedAt());
        return dto;
    }

    public Resort toResort(RegisterRequestDTO request) {
        Resort resort = new Resort();
        resort.setResortCode(request.getResortCode());
        resort.setStatus(ResortStatus.DRAFT);
        return resort;
    }

    public Resort toResort(RegisterBasicInfoRequestDTO request,int resortId) {
        Resort resort = resortRepository.findById(resortId)
        .orElseThrow(() -> new ResourceNotFoundException("Resort not found!"));
        resort.setName(request.getResortName());
        resort.setDescription(request.getDescription());
        resort.setCity(request.getCity());
        resort.setDistrict(request.getDistrict());
        resort.setAddress(request.getAddress());
        return resort;
    }

    public Resort toResort(RegisterCapacityPricingRequestDTO request,int resortId) {
        Resort resort = resortRepository.findById(resortId)
        .orElseThrow(() -> new ResourceNotFoundException("Resort not found!"));
        resort.setMaxGuest(request.getMaxGuest());
        resort.setPrice(request.getPrice());
        return resort;
    }

    public Resort toResort(RegisterAmenitiesRequestDTO request,int resortId) {
        Resort resort = resortRepository.findById(resortId)
        .orElseThrow(() -> new ResourceNotFoundException("Resort not found!"));

        List<ResortAmenity> amenities = resortAmenityRepository
            .findAllById(request.getAmenityIds());

        resort.setAmenities(new HashSet<>(amenities));
        return resort;
    }
    
}
