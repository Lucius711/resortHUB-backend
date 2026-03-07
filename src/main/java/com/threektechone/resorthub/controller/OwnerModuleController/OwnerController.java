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
import com.threektechone.resorthub.dto.OwnerModuleDTO.EditRequestDTO;
import com.threektechone.resorthub.dto.OwnerModuleDTO.OwnerResortsResponseDTO;
import com.threektechone.resorthub.dto.OwnerModuleDTO.RegisterAmenitiesRequestDTO;
import com.threektechone.resorthub.dto.OwnerModuleDTO.RegisterBasicInfoRequestDTO;
import com.threektechone.resorthub.dto.OwnerModuleDTO.RegisterCapacityPricingRequestDTO;
import com.threektechone.resorthub.dto.OwnerModuleDTO.RegisterRequestDTO;
import com.threektechone.resorthub.enums.ResortStatus;
import com.threektechone.resorthub.service.OwnerModule.ResortService;

import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/api/owner")
@RequiredArgsConstructor
public class OwnerController {

    private final ResortService resortService;
     
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
    public ResponseEntity<ApiResponse<Integer>> createRegistrationResort(@RequestBody RegisterRequestDTO dto,Authentication authentication) {
        String email = authentication.getName();

        int resortId = resortService.createRegistrationResort(dto, email);
        
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

    

    @PostMapping("/send-edit-request")
    public ResponseEntity<ApiResponse<String>> sendEditRequest(@RequestBody EditRequestDTO dto, Authentication authentication) {
        String email = authentication.getName();
        resortService.createEditRequest(dto, email);

        ApiResponse<String> response = new ApiResponse<>(201,null,"Send request successfully!",LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    
    

    

    

}