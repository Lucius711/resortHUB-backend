package com.threektechone.resorthub.controller.CustomerModuleController;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.threektechone.resorthub.common.response.ApiResponse;
import com.threektechone.resorthub.dto.CustomerModuleDTO.BookingRequestDTO;
import com.threektechone.resorthub.service.CustomerModule.BookingService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final BookingService bookingService;
    
    @PostMapping("/resorts/{id}/booking")
    public ResponseEntity<ApiResponse<String>> createBooking(@RequestBody BookingRequestDTO dto, @PathVariable int id, Authentication authentication) {
        
        String email = authentication.getName();
        bookingService.createBooking(dto, email, id);

        ApiResponse<String> response = new ApiResponse<>(200,null,"Create booking successfully!",LocalDateTime.now());
        return ResponseEntity.ok(response);
    }
}
