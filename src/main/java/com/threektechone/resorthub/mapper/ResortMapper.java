package com.threektechone.resorthub.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.threektechone.resorthub.dto.OwnerModuleDTO.OwnerResortsResponseDTO;
import com.threektechone.resorthub.dto.OwnerModuleDTO.RegisterAmenitiesRequestDTO;
import com.threektechone.resorthub.dto.OwnerModuleDTO.RegisterBasicInfoRequestDTO;
import com.threektechone.resorthub.dto.OwnerModuleDTO.RegisterCapacityPricingRequestDTO;
import com.threektechone.resorthub.dto.OwnerModuleDTO.RegisterImagesRequestDTO;
import com.threektechone.resorthub.dto.StaffModuleDTO.RegisterResponseDetailDTO;
import com.threektechone.resorthub.dto.StaffModuleDTO.RegisterResponseListDTO;
import com.threektechone.resorthub.models.Resort;
import com.threektechone.resorthub.models.ResortAmenity;
import com.threektechone.resorthub.models.ResortImage;

@Mapper(componentModel = "spring")
public interface ResortMapper {
    
    @Mapping(target = "resortId", source = "resortId")
    @Mapping(target = "resortName", source = "resort.name")
    @Mapping(target = "resortStatus", source = "resort.status")
    @Mapping(target = "approvedByName", source = "resort.staff.fullName")
    @Mapping(target = "approvedByPhone", source = "resort.staff.phone")
    @Mapping(target = "city", source = "city")
    @Mapping(target = "district", source = "district")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "createdAt", source = "createdAt")
    OwnerResortsResponseDTO toOwnerResortList(Resort resort);
    
    @Mapping(target = "resortId", source = "resortId")
    @Mapping(target = "resortCode", source = "resortCode")
    @Mapping(target = "resortName", source = "name")
    @Mapping(target = "resortStatus", source = "status")
    @Mapping(target = "ownerName", source = "resort.owner.fullName")
    @Mapping(target = "ownerPhone", source = "resort.owner.phone")
    RegisterResponseListDTO toRegisterResponseListDTO(Resort resort);
    
    @Mapping(target = "resortId", source = "resortId")
    @Mapping(target = "resortCode", source = "resortCode")
    @Mapping(target = "ownerName", source = "owner.fullName")
    @Mapping(target = "resortName", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "city", source = "city")
    @Mapping(target = "district", source = "address")
    @Mapping(target = "maxGuest", source = "maxGuest")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "amenityIds", source = "amenities")
    @Mapping(target = "imageIds", source = "images")
    RegisterResponseDetailDTO toRegisterResponseDetailDTO(Resort resort);
    
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "name", source = "resortName")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "city", source = "city")
    @Mapping(target = "district", source = "district")
    @Mapping(target = "address", source = "address")
    void updateResortBasicInfo(@MappingTarget Resort resort, RegisterBasicInfoRequestDTO dto);
    
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "maxGuest", source = "maxGuest")
    @Mapping(target = "price", source = "price")
    void updateResortCapacityPrice(@MappingTarget Resort resort, RegisterCapacityPricingRequestDTO dto);
    
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "amenities", source = "amenityIds")
    void updateResortAmenities(@MappingTarget Resort resort, RegisterAmenitiesRequestDTO dto);
    
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "images", source = "imageUrls")
    void updateResortImages(@MappingTarget Resort resort, RegisterImagesRequestDTO dto);

    default List<Integer> mapAmenities(Set<ResortAmenity> amenities) {
        if (amenities == null) return null;
        return amenities.stream()
            .map(ResortAmenity::getAmenityId)
            .toList();
    }


    default List<Integer> mapImages(List<ResortImage> images) {
        if (images == null) return null;
        return images.stream()
            .map(ResortImage::getImageId)
            .toList();
    }

    default Set<ResortAmenity> mapAmenityIds(List<Integer> amenityIds) {
        if (amenityIds == null) return null;

        return amenityIds.stream()
            .map(id -> {
                ResortAmenity ra = new ResortAmenity();
                ra.setAmenityId(id);
                return ra;
            })
            .collect(Collectors.toSet());
    }

    default List<ResortImage> mapImageIds(List<String> imageUrls) {
        if (imageUrls == null) return null;

        return imageUrls.stream()
            .map(url -> {
                ResortImage image = new ResortImage();
                image.setImageUrl(url);
                return image;
            })
            .collect(Collectors.toList());
    }
}
