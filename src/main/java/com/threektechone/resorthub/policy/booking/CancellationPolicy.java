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

        if (booking.getStatus() == BookingStatus.CANCELED) {
            throw new InvalidBookingStatusException("Booking is already canceled");
        }

        // Booking can't be canceled once it has progressed to/from stay.
        if (booking.getStatus() == BookingStatus.CHECKED_IN || booking.getStatus() == BookingStatus.COMPLETED) {
            throw new InvalidBookingStatusException("Cannot cancel booking after check-in");
        }

        // If the owner rejected the reservation, the customer should not be able to cancel it.
        if (booking.getStatus() == BookingStatus.REJECTED) {
            throw new InvalidBookingStatusException("Cannot cancel a rejected booking");
        }

        // Only allow cancellation for statuses that still represent a pending approval / accepted booking.
        if (booking.getStatus() != BookingStatus.PENDING && booking.getStatus() != BookingStatus.APPROVED) {
            throw new InvalidBookingStatusException("Cannot cancel booking in current status");
        }

        if (booking.getStatus() == BookingStatus.APPROVED
                && LocalDateTime.now().isAfter(booking.getCheckInDate().minusHours(24))) {
            throw new CancellationDeadlineException("Too late to cancel");
        }
    }

}
