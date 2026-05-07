package com.threektechone.resorthub.service.impl.admin;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.threektechone.resorthub.dto.admin.ActiveUserDTO;
import com.threektechone.resorthub.dto.admin.GrowthDTO;
import com.threektechone.resorthub.dto.admin.NewVsReturningDTO;
import com.threektechone.resorthub.dto.admin.PeriodDTO;
import com.threektechone.resorthub.dto.admin.UserByRoleDTO;
import com.threektechone.resorthub.dto.admin.UserChartDTO;
import com.threektechone.resorthub.dto.admin.UserDashboardDto;
import com.threektechone.resorthub.enums.UserStatus;
import com.threektechone.resorthub.mapper.chart.ChartMapper;
import com.threektechone.resorthub.repositories.UserRepository;
import com.threektechone.resorthub.repositories.projection.UserChartProjection;
import com.threektechone.resorthub.repositories.projection.UserRoleCountProjection;
import com.threektechone.resorthub.service.admin.UserDashboardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDashboardServiceImpl implements UserDashboardService {

    private final UserRepository userRepository;
    private final ChartMapper chartMapper;

    @Override
    @Cacheable(cacheNames = "admin-user-dashboard", key = "'dashboard'", unless = "#result == null")
    public UserDashboardDto getDashboard() {
        LocalDate today = LocalDate.now();

        LocalDateTime startToday = today.atStartOfDay();
        LocalDateTime endToday = today.plusDays(1).atStartOfDay();

        LocalDateTime startYesterday = today.minusDays(1).atStartOfDay();
        LocalDateTime endYesterday = today.atStartOfDay();

        LocalDateTime start7Days = today.minusDays(7).atStartOfDay();

        int total = userRepository.countTotalUsers();

        int todayCount = userRepository.countUsersByCreatedAtBetween(startToday, endToday);
        int yesterdayCount = userRepository.countUsersByCreatedAtBetween(startYesterday, endYesterday);

        double percentage = yesterdayCount == 0 ? 100
                : (todayCount - yesterdayCount) * 100.0 / yesterdayCount;
        PeriodDTO dailyPeriod = new PeriodDTO(todayCount, yesterdayCount, percentage);
        GrowthDTO growth = new GrowthDTO(dailyPeriod);

        int dailyActive = userRepository.countActiveUsers(UserStatus.ACTIVE, startToday, endToday);
        int weeklyActive = userRepository.countActiveUsers(UserStatus.ACTIVE, start7Days, endToday);
        ActiveUserDTO active = new ActiveUserDTO(dailyActive, weeklyActive);

        List<UserRoleCountProjection> roleData = userRepository.countByRole();
        UserByRoleDTO byRole = chartMapper.toUserByRoleDTO(roleData);

        int newUsers = userRepository.countUsersByCreatedAtBetween(start7Days, LocalDateTime.now());
        int returningUsers = total - newUsers;

        NewVsReturningDTO newVsReturning = new NewVsReturningDTO(newUsers, returningUsers);

        List<UserChartProjection> chartData = userRepository.getUserChart(start7Days);
        List<UserChartDTO> chart = chartMapper.toUserChartDTOList(chartData);

        return new UserDashboardDto(total, growth, active, byRole, newVsReturning, chart);
    }

}