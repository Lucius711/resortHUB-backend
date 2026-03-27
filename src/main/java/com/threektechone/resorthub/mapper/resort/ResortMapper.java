package com.threektechone.resorthub.mapper.resort;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.threektechone.resorthub.dto.owner.OwnerResortsDetailResponseDTO;
import com.threektechone.resorthub.dto.owner.OwnerResortsListResponseDTO;
import com.threektechone.resorthub.dto.owner.RegisterAmenitiesRequestDTO;
import com.threektechone.resorthub.dto.owner.RegisterBasicInfoRequestDTO;
import com.threektechone.resorthub.dto.owner.RegisterCapacityPricingRequestDTO;
import com.threektechone.resorthub.dto.owner.RegisterImagesRequestDTO;
import com.threektechone.resorthub.dto.owner.RegisterMenusRequestDTO;
import com.threektechone.resorthub.dto.pub.PublicResortResponseDetailDTO;
import com.threektechone.resorthub.dto.pub.PublicResortResponseListDTO;
import com.threektechone.resorthub.dto.staff.RegisterResponseDetailDTO;
import com.threektechone.resorthub.dto.staff.RegisterResponseListDTO;
import com.threektechone.resorthub.models.Resort;
import com.threektechone.resorthub.models.ResortAmenity;
import com.threektechone.resorthub.models.ResortImage;
import com.threektechone.resorthub.models.ResortMenu;
import com.threektechone.resorthub.models.ResortReview;

@Mapper(componentModel = "spring",uses = ResortMenuMapper.class)
public interface ResortMapper {
    
    @Mapping(target = "resortId", source = "resortId")
    @Mapping(target = "resortName", source = "resort.name")
    @Mapping(target = "resortStatus", source = "resort.status")
    @Mapping(target = "approvedByName", source = "resort.staff.fullName")
    @Mapping(target = "approvedByPhone", source = "resort.staff.phone")
    OwnerResortsListResponseDTO toOwnerResortList(Resort resort);
    
    @Mapping(target = "resortId", source = "resortId")
    @Mapping(target = "resortName", source = "resort.name")
    @Mapping(target = "resortStatus", source = "resort.status")
    @Mapping(target = "approvedByName", source = "resort.staff.fullName")
    @Mapping(target = "approvedByPhone", source = "resort.staff.phone")
    @Mapping(target = "amenityIds", source = "amenities")
    @Mapping(target = "imageIds", source = "images")
    @Mapping(target = "menuIds", source = "menuItems")
    @Mapping(target = "reviewIds", source = "reviews")
    OwnerResortsDetailResponseDTO toOwnerResortsDetail(Resort resort);
    
    
    @Mapping(target = "resortName", source = "name")
    @Mapping(target = "resortStatus", source = "status")
    @Mapping(target = "ownerName", source = "resort.owner.fullName")
    @Mapping(target = "ownerPhone", source = "resort.owner.phone")
    RegisterResponseListDTO toRegisterResponseListDTO(Resort resort);
    
    @Mapping(target = "resortName", source = "name")
    @Mapping(target = "thumbnail", expression = "java(getThumbnail(resort))")
    @Mapping(target = "maxGuest", source = "maxGuest")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "averageRating", source = "averageRating")
    PublicResortResponseListDTO toPublicResortResponseListDTO(Resort resort);
    

    @Mapping(target = "resortName", source = "name")
    @Mapping(target = "amenityIds", source = "amenities")
    @Mapping(target = "imageIds", source = "images")
    @Mapping(target = "menuIds", source = "menuItems")
    @Mapping(target = "reviewIds", source = "reviews")
    PublicResortResponseDetailDTO toPublicResortResponseDetailDTO(Resort resort);
    
    @Mapping(target = "resortCode", source = "resortCode")
    @Mapping(target = "ownerName", source = "owner.fullName")
    @Mapping(target = "resortName", source = "name")
    @Mapping(target = "amenityIds", source = "amenities")
    @Mapping(target = "imageIds", source = "images")
    RegisterResponseDetailDTO toRegisterResponseDetailDTO(Resort resort);
    
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "name", source = "resortName")
    void updateResortBasicInfo(@MappingTarget Resort resort, RegisterBasicInfoRequestDTO dto);
    
    @BeanMapping(ignoreByDefault = true)
    void updateResortCapacityPrice(@MappingTarget Resort resort, RegisterCapacityPricingRequestDTO dto);
    
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "amenities", source = "amenityIds")
    void updateResortAmenities(@MappingTarget Resort resort, RegisterAmenitiesRequestDTO dto);
    
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "images", source = "imageUrls")
    void updateResortImages(@MappingTarget Resort resort, RegisterImagesRequestDTO dto);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "menuItems", source = "menus")
    void updateResortMenu(@MappingTarget Resort resort, RegisterMenusRequestDTO dto);
    

    default String getThumbnail(Resort resort) {
        if (resort.getImages() == null || resort.getImages().isEmpty()) {
            return null;
        }
        return resort.getImages().get(0).getImageUrl();
    }

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

    default List<Integer> mapMenus(List<ResortMenu> menus) {
        if (menus == null) return null;
        return menus.stream()
            .map(ResortMenu::getMenuId)
            .toList();
    }

    default List<Integer> mapReviews(List<ResortReview> reviews) {
        if (reviews == null) return null;
        return reviews.stream()
            .map(ResortReview::getReviewId)
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
