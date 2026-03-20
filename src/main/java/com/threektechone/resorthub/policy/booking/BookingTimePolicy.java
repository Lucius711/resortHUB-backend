package com.threektechone.resorthub.policy.booking;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.threektechone.resorthub.models.Booking;

@Component
public class BookingTimePolicy {

    private static final int EARLY_CHECKIN_HOURS = 2;

    private static final int LATE_CHECKIN_HOURS = 2;

    private static final int LATE_CHECKOUT_HOURS = 2;

    private static final int EARLY_CHECKOUT_HOURS = 0;

    public Boolean isCheckInTimeValid(Booking booking) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime earliestAllowed = booking.getCheckInDate().minusHours(EARLY_CHECKIN_HOURS);
        LocalDateTime latestAllowed = booking.getCheckInDate().plusHours(LATE_CHECKIN_HOURS);

        return (now.isAfter(earliestAllowed) || now.isEqual(earliestAllowed))
                && (now.isBefore(latestAllowed) || now.isEqual(latestAllowed));
    }

    public Boolean isCheckOutTimeValid(Booking booking) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime earliestAllowed = booking.getCheckOutDate().minusHours(EARLY_CHECKOUT_HOURS);
        LocalDateTime latestAllowed = booking.getCheckOutDate().plusHours(LATE_CHECKOUT_HOURS);

        return (now.isAfter(earliestAllowed) || now.isEqual(earliestAllowed))
                && (now.isBefore(latestAllowed) || now.isEqual(latestAllowed));
    }
    
}
