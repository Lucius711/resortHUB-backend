package com.threektechone.resorthub.service.impl.OwnerModuleImpl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.threektechone.resorthub.common.exception.custom.ResourceNotFoundException;
import com.threektechone.resorthub.dto.OwnerModuleDTO.OwnerBookingDetailResponseDTO;
import com.threektechone.resorthub.dto.OwnerModuleDTO.OwnerBookingListResponseDTO;
import com.threektechone.resorthub.enums.BookingStatus;
import com.threektechone.resorthub.helper.BookingHelper.BookingPriceCalculator;
import com.threektechone.resorthub.mapper.BookingMapper;
import com.threektechone.resorthub.models.Booking;
import com.threektechone.resorthub.repositories.BookingRepository;
import com.threektechone.resorthub.service.OwnerModule.OwnerBookingService;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class OwnerBookingServiceImpl implements OwnerBookingService {
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final BookingPriceCalculator bookingPriceCalculator;

    @Override
    public Page<OwnerBookingListResponseDTO> getOwnerBookings(String email, String searchkey, BookingStatus status, Pageable pageable) {
        Page<Booking> bookingList = bookingRepository.getOwnerBookings(email, searchkey, status, pageable);

        return bookingList.map(bookingMapper::toOwnerBookingListResponseDTO);
    }

    @Override
    public OwnerBookingDetailResponseDTO getOwnerBookingDetail(int bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
        .orElseThrow(() -> new ResourceNotFoundException("Booking not found!"));

        OwnerBookingDetailResponseDTO dto = bookingMapper.tOwnerBookingDetailResponseDTO(booking);
        dto.setMealPrice(bookingPriceCalculator.calculateMealCost(dto.getMeals()));

        return dto;
    }
    
}
