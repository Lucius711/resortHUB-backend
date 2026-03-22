package com.threektechone.resorthub.policy.booking;

import org.springframework.stereotype.Component;

import com.threektechone.resorthub.enums.BookingStatus;
import com.threektechone.resorthub.enums.PaymentStatus;
import com.threektechone.resorthub.models.Booking;

@Component
public class CheckInPolicy {
    
    public Boolean canCheckIn(Booking booking, Boolean roomAvailable, Boolean isCheckInTimeValid) {
        boolean paid = booking.getPayment() != null
                && booking.getPayment().getPaymentStatus() == PaymentStatus.SUCCESS;
        return booking.getStatus() == BookingStatus.APPROVED && paid && roomAvailable && isCheckInTimeValid;
    }

}
