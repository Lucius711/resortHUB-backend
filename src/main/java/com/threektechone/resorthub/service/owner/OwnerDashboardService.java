package com.threektechone.resorthub.service.owner;

import java.util.List;

import com.threektechone.resorthub.dto.owner.OwnerBookingChartDTO;
import com.threektechone.resorthub.dto.owner.OwnerOverviewResponseDTO;
import com.threektechone.resorthub.dto.owner.OwnerRevenueChartDTO;

public interface OwnerDashboardService {
    OwnerOverviewResponseDTO getOwnerOverview(String ownerEmail);
    List<OwnerRevenueChartDTO> getOwnerRevenueChart(String ownerEmail);
    List<OwnerBookingChartDTO> getOwnerBookingChart(String ownerEmail);
}
