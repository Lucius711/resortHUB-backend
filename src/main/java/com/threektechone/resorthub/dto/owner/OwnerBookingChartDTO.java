package com.threektechone.resorthub.dto.owner;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OwnerBookingChartDTO {
    private LocalDate date;
    private int totalBookings;
    
}
