package com.threektechone.resorthub.controller.OwnerModuleController;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.threektechone.resorthub.common.response.ApiResponse;
import com.threektechone.resorthub.dto.OwnerModuleDTO.BookingRequestDecisionDTO;
import com.threektechone.resorthub.dto.OwnerModuleDTO.EditRequestDTO;
import com.threektechone.resorthub.dto.OwnerModuleDTO.OwnerBookingDetailResponseDTO;
import com.threektechone.resorthub.dto.OwnerModuleDTO.OwnerBookingListResponseDTO;
import com.threektechone.resorthub.dto.OwnerModuleDTO.OwnerResortsResponseDTO;
import com.threektechone.resorthub.dto.OwnerModuleDTO.RegisterAmenitiesRequestDTO;
import com.threektechone.resorthub.dto.OwnerModuleDTO.RegisterBasicInfoRequestDTO;
import com.threektechone.resorthub.dto.OwnerModuleDTO.RegisterCapacityPricingRequestDTO;
import com.threektechone.resorthub.dto.OwnerModuleDTO.RegisterImagesRequestDTO;
import com.threektechone.resorthub.dto.OwnerModuleDTO.RegisterMenusRequestDTO;
import com.threektechone.resorthub.enums.BookingStatus;
import com.threektechone.resorthub.enums.ResortStatus;
import com.threektechone.resorthub.service.OwnerModule.OwnerBookingService;
import com.threektechone.resorthub.service.OwnerModule.ResortService;

import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/api/owner")
@RequiredArgsConstructor
public class OwnerController {

    private final ResortService resortService;
    private final OwnerBookingService ownerBookingService;

     
    @GetMapping("/resorts")
    public ResponseEntity<ApiResponse<Page<OwnerResortsResponseDTO>>> getAllOwnerResorst(
        @RequestParam(required=false) String searchkey,
        @RequestParam(required=false)  ResortStatus status,
        @PageableDefault(size=5) Pageable pageable,
        Authentication authentication) {
        String email = authentication.getName();
          
        Page<OwnerResortsResponseDTO> resortList = resortService.getAllOwnerResorts(email,searchkey, status, pageable);

        ApiResponse<Page<OwnerResortsResponseDTO>> response = new ApiResponse<>(200,null,resortList,LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register-resort")
    public ResponseEntity<ApiResponse<Integer>> createRegistrationResort(Authentication authentication) {
        String email = authentication.getName();

        int resortId = resortService.createRegistrationResort(email);
        
        ApiResponse<Integer> response = new ApiResponse<>(201,null,resortId,LocalDateTime.now());
        return ResponseEntity.status(201).body(response);
    }

    @PatchMapping("/register-resort/{id}/basic-info")
    public ResponseEntity<ApiResponse<String>> updateBasicInfoResort(@RequestBody RegisterBasicInfoRequestDTO dto,@PathVariable int id) {

        resortService.updateBasicInfoResort(dto, id);
        
        ApiResponse<String> response = new ApiResponse<>(200,null,"Update basic info successfully!",LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/register-resort/{id}/capacity-price")
    public ResponseEntity<ApiResponse<String>> updateCapacityPriceResort(@RequestBody RegisterCapacityPricingRequestDTO dto,@PathVariable int id) {

        resortService.updateCapacityPriceResort(dto, id);
        
        ApiResponse<String> response = new ApiResponse<>(200,null,"Update capacity and price successfully!",LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/register-resort/{id}/amenities")
    public ResponseEntity<ApiResponse<String>> updateAmenitiesResort(@RequestBody RegisterAmenitiesRequestDTO dto,@PathVariable int id) {

        resortService.updateAmenitiesResort(dto, id);
        
        ApiResponse<String> response = new ApiResponse<>(200,null,"Update amenities successfully!",LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/register-resort/{id}/images")
    public ResponseEntity<ApiResponse<String>> updateImagesResort(@RequestBody RegisterImagesRequestDTO dto,@PathVariable int id) {

        resortService.updateImagesResort(dto, id);
        
        ApiResponse<String> response = new ApiResponse<>(200,null,"Update images successfully!",LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/register-resort/{id}/menus")
    public ResponseEntity<ApiResponse<String>> updateMenusResort(@RequestBody RegisterMenusRequestDTO dto,@PathVariable int id) {

        resortService.updateMenusResort(dto, id);
        
        ApiResponse<String> response = new ApiResponse<>(200,null,"Update menus successfully!",LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register-resort/{id}/submit")
    public ResponseEntity<ApiResponse<String>> submitRegisterResort(@PathVariable int id) {
        resortService.submitRegisterResort(id);
        
        ApiResponse<String> response = new ApiResponse<>(200,null,"Submit successfully!",LocalDateTime.now());

        return ResponseEntity.ok(response);
    }
     

    @PostMapping("/send-edit-request")
    public ResponseEntity<ApiResponse<String>> sendEditRequest(@RequestBody EditRequestDTO dto, Authentication authentication) {
        String email = authentication.getName();
        resortService.createEditRequest(dto, email);

        ApiResponse<String> response = new ApiResponse<>(201,null,"Send request successfully!",LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/bookings")
    public ResponseEntity<ApiResponse<Page<OwnerBookingListResponseDTO>>> getOwnerBookings(@RequestParam(required =false) String searchkey, @RequestParam(required = false) BookingStatus status, @PageableDefault(size =5) Pageable pageable,Authentication authentication) {
        String email = authentication.getName();

        Page<OwnerBookingListResponseDTO> dtoList = ownerBookingService.getOwnerBookings(email, searchkey, status, pageable);

        ApiResponse<Page<OwnerBookingListResponseDTO>> response = new ApiResponse<>(200,null,dtoList,LocalDateTime.now());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/bookings/{id}")
    public ResponseEntity<ApiResponse<OwnerBookingDetailResponseDTO>> getOwnerBookingDetail(@PathVariable int id) {
        OwnerBookingDetailResponseDTO dto = ownerBookingService.getOwnerBookingDetail(id);

        ApiResponse<OwnerBookingDetailResponseDTO> response = new ApiResponse<>(200,null,dto,LocalDateTime.now());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/bookings/{id}/review")
    public ResponseEntity<ApiResponse<String>> reviewBooking(@RequestBody BookingRequestDecisionDTO dto, @PathVariable int id) {
        ownerBookingService.reviewBooking(dto, id);
        
        ApiResponse<String> response = new ApiResponse<>(200,null,"Review successfully!",LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/bookings/{id}/check-in")
    public ResponseEntity<ApiResponse<String>> checkInBooking(@PathVariable int id) {
        ownerBookingService.checkIn(id);

        ApiResponse<String> response = new ApiResponse<>(200,null,"Check-in successfuly!",LocalDateTime.now());

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/bookings/{id}/check-out")
    public ResponseEntity<ApiResponse<String>> checkOutBooking(@PathVariable int id) {
        ownerBookingService.checkOut(id);

        ApiResponse<String> response = new ApiResponse<>(200,null,"Check-out successfuly!",LocalDateTime.now());

        return ResponseEntity.ok(response);
    }
}