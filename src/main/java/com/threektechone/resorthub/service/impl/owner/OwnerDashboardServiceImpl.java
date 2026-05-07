package com.threektechone.resorthub.service.impl.owner;

import java.time.LocalDate;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.threektechone.resorthub.dto.owner.OwnerBookingChartDTO;
import com.threektechone.resorthub.dto.owner.OwnerOverviewResponseDTO;
import com.threektechone.resorthub.dto.owner.OwnerRevenueChartDTO;
import com.threektechone.resorthub.mapper.chart.ChartMapper;
import com.threektechone.resorthub.repositories.BookingRepository;
import com.threektechone.resorthub.repositories.PaymentRepository;
import com.threektechone.resorthub.repositories.ResortRepository;
import com.threektechone.resorthub.repositories.ResortReviewRepository;
import com.threektechone.resorthub.service.owner.OwnerDashboardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OwnerDashboardServiceImpl implements OwnerDashboardService {

    private final ResortRepository resortRepository;
    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;
    private final ResortReviewRepository resortReviewRepository;
    private final ChartMapper chartMapper;

    @Override
    @Cacheable(cacheNames = "owner-dashboard-overview", key = "#ownerEmail", unless = "#result == null")
    public OwnerOverviewResponseDTO getOwnerOverview(String ownerEmail) {
        double totalRevenue = paymentRepository.getTotalRevenue(ownerEmail);
        double revenueThisMonth = paymentRepository.getRevenueThisMonth(ownerEmail);
        double revenueLastMonth = paymentRepository.getRevenueLastMonth(ownerEmail);

        double growthRate = 0.0;
        if (revenueLastMonth != 0) {
            growthRate = ((revenueThisMonth - revenueLastMonth) / revenueLastMonth) * 100;
        }

        double averageRating = resortReviewRepository.getAverageRating(ownerEmail);
        int totalReviews = resortReviewRepository.getTotalReviews(ownerEmail);
        int totalBookings = bookingRepository.getTotalBookings(ownerEmail);
        int bookingThisMonth = bookingRepository.getBookingThisMonth(ownerEmail);
        int totalResorts = resortRepository.getTotalResorts(ownerEmail);
        int activeResorts = resortRepository.getActiveResorts(ownerEmail);

        return new OwnerOverviewResponseDTO(
                totalRevenue,
                revenueThisMonth,
                revenueLastMonth,
                growthRate,
                averageRating,
                totalReviews,
                totalBookings,
                bookingThisMonth,
                totalResorts,
                activeResorts);
    }

    @Override
    @Cacheable(cacheNames = "owner-revenue-chart", key = "#ownerEmail", unless = "#result == null || #result.isEmpty()")
    public List<OwnerRevenueChartDTO> getOwnerRevenueChart(String ownerEmail) {
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusDays(29);
        List<OwnerRevenueChartDTO> chartData = paymentRepository.getRevenueChart(ownerEmail,
                startDate.atStartOfDay(),
                today.plusDays(1).atStartOfDay())
                .stream()
                .map(chartMapper::toOwnerRevenueChartDTO)
                .toList();

        return chartData;
    }

    @Override
    @Cacheable(cacheNames = "owner-booking-chart", key = "#ownerEmail", unless = "#result == null || #result.isEmpty()")
    public List<OwnerBookingChartDTO> getOwnerBookingChart(String ownerEmail) {
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.minusDays(29);
        List<OwnerBookingChartDTO> chartData = bookingRepository.getBookingChart(ownerEmail,
                startDate.atStartOfDay(),
                today.plusDays(1).atStartOfDay())
                .stream()
                .map(chartMapper::toOwnerBookingChartDTO)
                .toList();

        return chartData;
    }

}
