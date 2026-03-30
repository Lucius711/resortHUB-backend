package com.threektechone.resorthub.repositories.projection;

import java.time.LocalDate;

public interface OwnerRevenueChartProjection {
    LocalDate getDate();
    double getRevenue();
}
