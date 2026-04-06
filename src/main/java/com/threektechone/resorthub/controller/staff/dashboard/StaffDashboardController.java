package com.threektechone.resorthub.controller.staff.dashboard;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.threektechone.resorthub.common.response.ApiResponse;
import com.threektechone.resorthub.dto.staff.StaffDashboardDTO;
import com.threektechone.resorthub.service.staff.StaffDashboardService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/staff/dashboard")
@RequiredArgsConstructor
public class StaffDashboardController {
    
    private final StaffDashboardService staffDashboardService;

    @GetMapping
    public ResponseEntity<ApiResponse<StaffDashboardDTO>> getStaffDashboard(Authentication authentication) {
        String email = authentication.getName();
        StaffDashboardDTO dashboardData = staffDashboardService.getStaffDashboard(email);

        ApiResponse<StaffDashboardDTO> response = new ApiResponse<>(200,null, dashboardData,LocalDateTime.now());
        return ResponseEntity.ok(response);
    }
    
}
