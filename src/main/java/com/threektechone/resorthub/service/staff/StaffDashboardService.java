package com.threektechone.resorthub.service.staff;

import com.threektechone.resorthub.dto.staff.StaffDashboardDTO;

public interface StaffDashboardService {
    StaffDashboardDTO getStaffDashboard(String staffEmail);
}
