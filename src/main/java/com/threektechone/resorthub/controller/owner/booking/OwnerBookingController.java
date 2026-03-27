package com.threektechone.resorthub.controller.owner.booking;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.threektechone.resorthub.common.response.ApiResponse;
import com.threektechone.resorthub.dto.owner.BookingRequestDecisionDTO;
import com.threektechone.resorthub.dto.owner.OwnerBookingDetailResponseDTO;
import com.threektechone.resorthub.dto.owner.OwnerBookingListResponseDTO;
import com.threektechone.resorthub.enums.BookingStatus;
import com.threektechone.resorthub.service.owner.OwnerBookingService;

import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/api/owner/bookings")
@RequiredArgsConstructor
public class OwnerBookingController {
    private final OwnerBookingService ownerBookingService;

    @GetMapping("")
    public ResponseEntity<ApiResponse<Page<OwnerBookingListResponseDTO>>> getOwnerBookings(
        @RequestParam(required= false) String searchkey,
        @RequestParam(required = false) BookingStatus status,
        @PageableDefault(size=5) Pageable pageable,
        Authentication authentication) {
        
        String email = authentication.getName();
        Page<OwnerBookingListResponseDTO> bookingList = ownerBookingService.getOwnerBookings(email, searchkey, status, pageable);

        ApiResponse<Page<OwnerBookingListResponseDTO>> response =  new ApiResponse<>(200,null,bookingList,LocalDateTime.now());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<ApiResponse<OwnerBookingDetailResponseDTO>> getBookingDetail(
        @PathVariable int bookingId,
        Authentication authentication) {
        
            String email = authentication.getName();
            OwnerBookingDetailResponseDTO dto = ownerBookingService.getOwnerBookingDetail(bookingId, email);

            ApiResponse<OwnerBookingDetailResponseDTO> response = new ApiResponse<>(200,null,dto,LocalDateTime.now());
            return ResponseEntity.ok(response);
        
    }

    @PatchMapping("/{bookingId}/review")
    public ResponseEntity<ApiResponse<String>> reviewBooking(
        @PathVariable int bookingId,
        @RequestBody BookingRequestDecisionDTO dto,
        Authentication authentication) {
            
            String email = authentication.getName();
            ownerBookingService.reviewBooking(dto, bookingId, email);
            
            ApiResponse<String> response = new ApiResponse<>(200,null,"Review booking successfully!",LocalDateTime.now());

            return ResponseEntity.ok(response);
    }
    
    @PatchMapping("/{bookingId}/check-in")
    public ResponseEntity<ApiResponse<String>> checkIn(
        @PathVariable int bookingId,
        Authentication authentication) {

            String email = authentication.getName();
            ownerBookingService.checkIn(bookingId, email);

            ApiResponse<String> response = new ApiResponse<>(200,null,"Check in successfully!",LocalDateTime.now());
            return ResponseEntity.ok(response);
    }

    @PatchMapping("/{bookingId}/check-out")
    public ResponseEntity<ApiResponse<String>> checkOut(
        @PathVariable int bookingId,
        Authentication authentication) {

            String email = authentication.getName();
            ownerBookingService.checkOut(bookingId, email);

            ApiResponse<String> response = new ApiResponse<>(200,null,"Check out successfully!",LocalDateTime.now());
            return ResponseEntity.ok(response);
    }
    
    
}
