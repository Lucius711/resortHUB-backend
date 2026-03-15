package com.threektechone.resorthub.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.threektechone.resorthub.dto.CustomerModuleDTO.BookingRequestDTO;
import com.threektechone.resorthub.dto.CustomerModuleDTO.CustomerBookingDetailResponseDTO;
import com.threektechone.resorthub.dto.CustomerModuleDTO.CustomerBookingListResponseDTO;
import com.threektechone.resorthub.dto.OwnerModuleDTO.OwnerBookingDetailResponseDTO;
import com.threektechone.resorthub.dto.OwnerModuleDTO.OwnerBookingListResponseDTO;
import com.threektechone.resorthub.models.Booking;

@Mapper(componentModel = "spring", uses = BookingMealMapper.class)
public interface  BookingMapper {
    
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "checkInDate", source = "checkInDate")
    @Mapping(target = "checkOutDate", source = "checkOutDate")
    @Mapping(target = "numberOfPerson", source = "numberOfPerson")
    Booking toBooking(BookingRequestDTO dto);
    

    @Mapping(target = "resortName", source = "resort.name")
    @Mapping(target = "address", source = "resort.address")
    @Mapping(target = "bookingStatus", source = "status")
    @Mapping(target = "thumbnail", expression = "java(getThumbnail(booking))")
    CustomerBookingListResponseDTO toCustomerBookingListResponseDTO(Booking booking);


    @Mapping(target = "resortName", source = "resort.name")
    @Mapping(target = "resortCode", source = "resort.resortCode")
    @Mapping(target = "resortType", source = "resort.type")
    @Mapping(target = "ownerImage", source = "resort.owner.image")
    @Mapping(target = "ownerName", source = "resort.owner.fullName")
    @Mapping(target = "paymentStatus", source = "payment.paymentStatus")
    @Mapping(target = "mealPrice", ignore= true)
    CustomerBookingDetailResponseDTO toCustomerBookingDetailResponseDTO(Booking booking);
    

    @Mapping(target = "customerName", source = "customer.fullName")
    @Mapping(target = "customerImage", source = "customer.image")
    @Mapping(target = "resortName", source = "resort.name")
    OwnerBookingListResponseDTO toOwnerBookingListResponseDTO(Booking booking);

    @Mapping(target = "resortName", source = "resort.name")
    @Mapping(target = "resortCode", source = "resort.resortCode")
    @Mapping(target = "resortType", source = "resort.type")
    @Mapping(target = "customerImage", source = "customer.image")
    @Mapping(target = "customerName", source = "customer.fullName")
    @Mapping(target = "paymentStatus", source = "payment.paymentStatus")
    @Mapping(target = "bookingStatus", source = "status")
    @Mapping(target = "mealPrice", ignore= true)
    @Mapping(target = "roomAvailable", ignore= true)
    @Mapping(target = "canCheckIn", ignore= true)
    @Mapping(target = "canCheckOut", ignore= true)
    OwnerBookingDetailResponseDTO tOwnerBookingDetailResponseDTO(Booking booking);

    default String getThumbnail(Booking booking) {
        if (booking.getResort().getImages() == null || booking.getResort().getImages().isEmpty()) {
            return null;
        }
        return booking.getResort().getImages().get(0).getImageUrl();
    }
}
