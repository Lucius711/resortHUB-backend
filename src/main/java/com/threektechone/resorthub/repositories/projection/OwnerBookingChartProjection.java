package com.threektechone.resorthub.repositories.projection;

import java.time.LocalDate;

public interface OwnerBookingChartProjection {
    LocalDate getDate();
    int getTotalBookings();
}
