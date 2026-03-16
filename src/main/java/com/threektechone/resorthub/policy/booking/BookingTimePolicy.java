package com.threektechone.resorthub.policy.booking;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.threektechone.resorthub.models.Booking;

@Component
public class BookingTimePolicy {

    private static final int EARLY_CHECKIN_HOURS = 2;

    private static final int LATE_CHECKOUT_HOURS = 2;

    public Boolean isCheckInTimeValid(Booking booking) {

        LocalDateTime earlyCheckInTime =
                booking.getCheckInDate().minusHours(EARLY_CHECKIN_HOURS);

        return LocalDateTime.now().isAfter(earlyCheckInTime);
    }

    public Boolean isCheckOutTimeValid(Booking booking) {

        LocalDateTime lateCheckoutTime =
                booking.getCheckOutDate().plusHours(LATE_CHECKOUT_HOURS);

        return LocalDateTime.now().isBefore(lateCheckoutTime);
    }
    
}
