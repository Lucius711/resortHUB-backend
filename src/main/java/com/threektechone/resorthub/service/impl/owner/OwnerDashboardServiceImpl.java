package com.threektechone.resorthub.service.impl.owner;

import org.springframework.stereotype.Service;

import com.threektechone.resorthub.dto.owner.OwnerOverviewResponseDTO;
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

    @Override
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
    
}
