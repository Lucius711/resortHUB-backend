package com.threektechone.resorthub.policy.booking;

import org.springframework.stereotype.Component;

import com.threektechone.resorthub.enums.BookingStatus;
import com.threektechone.resorthub.models.Booking;

@Component
public class CheckInPolicy {
    
    public Boolean canCheckIn(Booking booking, Boolean roomAvailable,Boolean isCheckInTimeValid) {
       return booking.getStatus() == BookingStatus.APPROVED && roomAvailable && isCheckInTimeValid;
    }

}
