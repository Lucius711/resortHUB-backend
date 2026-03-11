package com.threektechone.resorthub.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.threektechone.resorthub.dto.CustomerModuleDTO.BookingRequestDTO;
import com.threektechone.resorthub.models.Booking;

@Mapper(componentModel = "spring", uses = BookingMealMapper.class)
public interface  BookingMapper {
    
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "checkInDate", source = "checkInDate")
    @Mapping(target = "checkOutDate", source = "checkOutDate")
    @Mapping(target = "numberOfPerson", source = "numberOfPerson")
    @Mapping(target = "meals", source = "meals")
    Booking toBooking(BookingRequestDTO dto);

}
