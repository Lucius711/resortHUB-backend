package com.threektechone.resorthub.dto.admin;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDashboardDto {
    private int total;
    private GrowthDTO growth;
    private ActiveUserDTO active;
    private UserByRoleDTO byRole;
    private NewVsReturningDTO newVsReturning;
    private List<UserChartDTO> chart;
}
