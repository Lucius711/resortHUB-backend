package com.threektechone.resorthub.mapper.chart;

import java.util.List;

import org.mapstruct.Mapper;

import com.threektechone.resorthub.dto.admin.UserByRoleDTO;
import com.threektechone.resorthub.dto.admin.UserChartDTO;
import com.threektechone.resorthub.dto.owner.OwnerBookingChartDTO;
import com.threektechone.resorthub.dto.owner.OwnerRevenueChartDTO;
import com.threektechone.resorthub.dto.staff.ResortStatusChartDTO;
import com.threektechone.resorthub.repositories.projection.OwnerBookingChartProjection;
import com.threektechone.resorthub.repositories.projection.OwnerRevenueChartProjection;
import com.threektechone.resorthub.repositories.projection.ResortStatusCountProjection;
import com.threektechone.resorthub.repositories.projection.UserChartProjection;
import com.threektechone.resorthub.repositories.projection.UserRoleCountProjection;

@Mapper(componentModel = "spring")
public interface ChartMapper {

    OwnerRevenueChartDTO toOwnerRevenueChartDTO(OwnerRevenueChartProjection projection);

    OwnerBookingChartDTO toOwnerBookingChartDTO(OwnerBookingChartProjection projection);

    ResortStatusChartDTO toResortStatusChartDTO(ResortStatusCountProjection projection);

    UserChartDTO toUserChartDTO(UserChartProjection projection);

    List<UserChartDTO> toUserChartDTOList(List<UserChartProjection> projections);

    default UserByRoleDTO toUserByRoleDTO(List<UserRoleCountProjection> projections) {
        UserByRoleDTO dto = new UserByRoleDTO();

        for (UserRoleCountProjection p : projections) {
            switch (p.getRoleName().toUpperCase()) {
                case "CUSTOMER" -> dto.setCustomer(p.getCount());
                case "OWNER" -> dto.setOwner(p.getCount());
                case "STAFF" -> dto.setStaff(p.getCount());
            }
        }

        return dto;
    }
}
