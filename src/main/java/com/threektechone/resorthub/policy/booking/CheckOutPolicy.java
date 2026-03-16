package com.threektechone.resorthub.policy.booking;

import org.springframework.stereotype.Component;

import com.threektechone.resorthub.enums.BookingStatus;
import com.threektechone.resorthub.models.Booking;

@Component
public class CheckOutPolicy {
    
    public Boolean canCheckOut(Booking booking, Boolean isCheckOutTimeValid) {
        return booking.getStatus() == BookingStatus.CHECKED_IN && isCheckOutTimeValid;
    }
}
