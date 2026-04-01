package com.threektechone.resorthub.mapper.chart;

import org.mapstruct.Mapper;

import com.threektechone.resorthub.dto.owner.OwnerBookingChartDTO;
import com.threektechone.resorthub.dto.owner.OwnerRevenueChartDTO;
import com.threektechone.resorthub.dto.staff.ResortStatusChartDTO;
import com.threektechone.resorthub.repositories.projection.OwnerBookingChartProjection;
import com.threektechone.resorthub.repositories.projection.OwnerRevenueChartProjection;
import com.threektechone.resorthub.repositories.projection.ResortStatusCountProjection;

@Mapper(componentModel = "spring")
public interface ChartMapper {
    
    OwnerRevenueChartDTO toOwnerRevenueChartDTO(OwnerRevenueChartProjection projection);

    OwnerBookingChartDTO toOwnerBookingChartDTO(OwnerBookingChartProjection projection);

    ResortStatusChartDTO toResortStatusChartDTO(ResortStatusCountProjection projection);
}
