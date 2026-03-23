package com.threektechone.resorthub.dto.owner;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.threektechone.resorthub.enums.BookingStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OwnerBookingListResponseDTO {
    private int bookingId;
    private String bookingCode;
    private String customerName;
    private String customerImage;
    private String resortName;
    private BookingStatus status;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    private int numberOfPerson;
    private BigDecimal totalPrice;
}
