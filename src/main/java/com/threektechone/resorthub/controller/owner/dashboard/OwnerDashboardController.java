package com.threektechone.resorthub.controller.owner.dashboard;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.threektechone.resorthub.common.response.ApiResponse;
import com.threektechone.resorthub.dto.owner.OwnerOverviewResponseDTO;
import com.threektechone.resorthub.service.owner.OwnerDashboardService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/owner/dashboard")
@RequiredArgsConstructor
public class OwnerDashboardController {
    private final OwnerDashboardService ownerDashboardService;
    
    @GetMapping("/overview")
    public ResponseEntity<ApiResponse<OwnerOverviewResponseDTO>> getOverview(Authentication authentication) {
        String ownerEmail = authentication.getName();
        OwnerOverviewResponseDTO overview = ownerDashboardService.getOwnerOverview(ownerEmail);

        ApiResponse<OwnerOverviewResponseDTO> response = new ApiResponse<>(200,null, overview, LocalDateTime.now());
        return ResponseEntity.ok(response);
    }
    
}
