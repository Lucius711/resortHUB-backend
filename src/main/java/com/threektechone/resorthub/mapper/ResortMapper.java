package com.threektechone.resorthub.mapper;

import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Component;

import com.threektechone.resorthub.ExceptionHandler.CustomException.ResourceNotFoundException;
import com.threektechone.resorthub.dto.OwnerModuleDTO.OwnerResortsResponseDTO;
import com.threektechone.resorthub.dto.OwnerModuleDTO.RegisterAmenitiesRequestDTO;
import com.threektechone.resorthub.dto.OwnerModuleDTO.RegisterBasicInfoRequestDTO;
import com.threektechone.resorthub.dto.OwnerModuleDTO.RegisterCapacityPricingRequestDTO;
import com.threektechone.resorthub.dto.OwnerModuleDTO.RegisterImagesRequestDTO;
import com.threektechone.resorthub.dto.StaffModuleDTO.RegisterResponseDetailDTO;
import com.threektechone.resorthub.dto.StaffModuleDTO.RegisterResponseListDTO;
import com.threektechone.resorthub.enums.ContractStatus;
import com.threektechone.resorthub.models.Resort;
import com.threektechone.resorthub.models.ResortAmenity;
import com.threektechone.resorthub.models.ResortImage;
import com.threektechone.resorthub.repositories.ResortAmenityRepository;
import com.threektechone.resorthub.repositories.ResortImageRepository;
import com.threektechone.resorthub.repositories.ResortRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ResortMapper {

    private final ResortRepository resortRepository;
    private final ResortAmenityRepository resortAmenityRepository;
    private final ResortImageRepository resortImageRepository;

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

    public Resort toResort(RegisterImagesRequestDTO request,int resortId) {
        Resort resort = resortRepository.findById(resortId)
        .orElseThrow(() -> new ResourceNotFoundException("Resort not found!"));

        List<ResortImage> images = resortImageRepository
            .findAllById(request.getImageIds());

        resort.getImages().clear();

        for (ResortImage img : images) {
            img.setResort(resort);
            resort.getImages().add(img);
        }
        return resort;
    }

    public RegisterResponseListDTO toRegisterResponseListDTO(Resort resort) {
        RegisterResponseListDTO dto = new RegisterResponseListDTO();
        dto.setResortId(resort.getResortId());
        dto.setResortName(resort.getName());
        dto.setResortCode(resort.getResortCode());
        dto.setOwnerName(resort.getOwner().getFullName());
        dto.setOwnerPhone(resort.getOwner().getPhone());
        return dto;
    }

    public RegisterResponseDetailDTO toRegisterResponseDetailDTO(Resort resort) {
        RegisterResponseDetailDTO dto = new RegisterResponseDetailDTO();
        dto.setResortId(resort.getResortId());
        dto.setResortName(resort.getName());
        dto.setOwnerName(resort.getOwner().getFullName());
        dto.setResortCode(resort.getResortCode());
        dto.setDescription(resort.getDescription());
        dto.setAddress(resort.getAddress());
        dto.setCity(resort.getCity());
        dto.setDistrict(resort.getDistrict());
        dto.setMaxGuest(resort.getMaxGuest());
        dto.setPrice(resort.getPrice());
        dto.setAmenityIds(resort.getAmenities().stream().map(ResortAmenity::getAmenityId).toList());
        dto.setImageIds(resort.getImages().stream().map(ResortImage::getImageId).toList());
        return dto;
    }
    
}
