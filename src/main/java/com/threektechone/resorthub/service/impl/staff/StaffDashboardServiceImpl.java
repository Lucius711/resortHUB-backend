package com.threektechone.resorthub.service.impl.staff;

import java.util.List;

import org.springframework.stereotype.Service;

import com.threektechone.resorthub.dto.staff.ResortStatusChartDTO;
import com.threektechone.resorthub.dto.staff.StaffDashboardDTO;
import com.threektechone.resorthub.enums.ResortStatus;
import com.threektechone.resorthub.mapper.chart.ChartMapper;
import com.threektechone.resorthub.repositories.ResortRepository;
import com.threektechone.resorthub.service.staff.StaffDashboardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StaffDashboardServiceImpl implements StaffDashboardService {

    private final ResortRepository resortRepository;
    private final ChartMapper chartMapper;

    @Override
    public StaffDashboardDTO getStaffDashboard(String staffEmail) {
        StaffDashboardDTO dto = new StaffDashboardDTO();

        dto.setPendingResorts(resortRepository.countByStatusAndStaffEmail(ResortStatus.PENDING_REVIEW, staffEmail));
        dto.setApprovedResorts(resortRepository.countByStatusAndStaffEmail(ResortStatus.APPROVED, staffEmail));
        dto.setRejectedResorts(resortRepository.countByStatusAndStaffEmail(ResortStatus.REJECTED, staffEmail));

        List<ResortStatusChartDTO> statusChart = resortRepository.countResortsGroupByStatus(staffEmail)
                .stream()
                .map(chartMapper::toResortStatusChartDTO)
                .toList();
        dto.setStatusChart(statusChart);
        return dto;
    }
    
}
