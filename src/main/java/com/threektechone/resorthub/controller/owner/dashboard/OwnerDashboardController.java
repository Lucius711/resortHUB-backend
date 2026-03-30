package com.threektechone.resorthub.controller.owner.dashboard;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.threektechone.resorthub.common.response.ApiResponse;
import com.threektechone.resorthub.dto.owner.OwnerBookingChartDTO;
import com.threektechone.resorthub.dto.owner.OwnerDashboardResponseDTO;
import com.threektechone.resorthub.dto.owner.OwnerOverviewResponseDTO;
import com.threektechone.resorthub.dto.owner.OwnerRevenueChartDTO;
import com.threektechone.resorthub.service.owner.OwnerDashboardService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/owner/dashboard")
@RequiredArgsConstructor
public class OwnerDashboardController {
    private final OwnerDashboardService ownerDashboardService;
    
    @GetMapping
    public ResponseEntity<ApiResponse<OwnerDashboardResponseDTO>> getDashboard(Authentication authentication) {
        String ownerEmail = authentication.getName();
        OwnerOverviewResponseDTO overview = ownerDashboardService.getOwnerOverview(ownerEmail);
        List<OwnerRevenueChartDTO> revenueChart = ownerDashboardService.getOwnerRevenueChart(ownerEmail);
        List<OwnerBookingChartDTO> bookingChart = ownerDashboardService.getOwnerBookingChart(ownerEmail);

        OwnerDashboardResponseDTO dashboardData = new OwnerDashboardResponseDTO(overview, revenueChart, bookingChart);
        ApiResponse<OwnerDashboardResponseDTO> response = new ApiResponse<>(200, null, dashboardData, LocalDateTime.now());
        return ResponseEntity.ok(response);
    }
    
}
