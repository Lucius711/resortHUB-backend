package com.threektechone.resorthub.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.threektechone.resorthub.service.CustomerModule.CustomerBookingService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BookingScheduler {
    private final CustomerBookingService bookingService;
    
    @Scheduled(fixedRate = 600000)
    public void cancelExpiredBookings() {
        bookingService.cancelExpiredBookings();
    }

}
