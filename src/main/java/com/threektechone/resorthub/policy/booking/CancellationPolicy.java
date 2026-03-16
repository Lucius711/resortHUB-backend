package com.threektechone.resorthub.policy.booking;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.threektechone.resorthub.common.exception.custom.BookingCancelForbiddenException;
import com.threektechone.resorthub.common.exception.custom.CancellationDeadlineException;
import com.threektechone.resorthub.common.exception.custom.InvalidBookingStatusException;
import com.threektechone.resorthub.enums.BookingStatus;
import com.threektechone.resorthub.models.Booking;

@Component
public class CancellationPolicy {
    
    public void validateCancel(Booking booking, String customerMail) {
        if (!booking.getCustomer().getEmail().equalsIgnoreCase(customerMail)) {
            throw new BookingCancelForbiddenException("You are not have permission to cancel this booking!");
        }

        if (booking.getStatus().equals(BookingStatus.CHECKED_IN) || booking.getStatus().equals(BookingStatus.COMPLETED)) {
            throw new InvalidBookingStatusException("Cannot cancel booking after check-in");
        }

        if (booking.getStatus().equals(BookingStatus.APPROVED) && LocalDateTime.now().isAfter(booking.getCheckInDate().minusHours(24))) {
            throw new CancellationDeadlineException("Too late to cancel");
        }
    }

}
