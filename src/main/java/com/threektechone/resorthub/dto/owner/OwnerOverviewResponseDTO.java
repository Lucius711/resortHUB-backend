package com.threektechone.resorthub.dto.owner;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OwnerOverviewResponseDTO {
    private double totalRevenue;
    private double revenueThisMonth;
    private double revenueLastMonth;
    private double revenueGrowthRate;

    private double averageRating;
    private int totalReviews;

    private int totalBookings;
    private int bookingThisMonth;

    private int totalResorts;
    private int activeResorts;
}
