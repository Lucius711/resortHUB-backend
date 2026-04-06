package com.threektechone.resorthub.controller.admin.dashboard;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.threektechone.resorthub.common.response.ApiResponse;
import com.threektechone.resorthub.dto.admin.UserDashboardDto;
import com.threektechone.resorthub.service.admin.UserDashboardService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/admin/dashboard")
@RequiredArgsConstructor
public class UserDashboardController {
    private final UserDashboardService userDashboardService;

    @GetMapping
    public ResponseEntity<ApiResponse<UserDashboardDto>> getDashboard() {
        UserDashboardDto dashboardData = userDashboardService.getDashboard();

        ApiResponse<UserDashboardDto> response = new ApiResponse<>(200, null, dashboardData, LocalDateTime.now());
        return ResponseEntity.ok(response);
    }
    
    
}
