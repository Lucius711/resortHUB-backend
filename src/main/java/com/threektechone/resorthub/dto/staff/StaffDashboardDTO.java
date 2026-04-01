package com.threektechone.resorthub.dto.staff;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class StaffDashboardDTO {
    private int pendingResorts;
    private int approvedResorts;
    private int rejectedResorts;
    private List<ResortStatusChartDTO> statusChart;
}
