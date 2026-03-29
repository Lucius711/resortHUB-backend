package com.threektechone.resorthub.service.owner;

import com.threektechone.resorthub.dto.owner.OwnerOverviewResponseDTO;

public interface OwnerDashboardService {
    OwnerOverviewResponseDTO getOwnerOverview(String ownerEmail);
}
