package com.threektechone.resorthub.dto.CustomerModuleDTO;

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
public class CustomerBookingListResponseDTO {
    private int bookingId;
    private String resortName;
    private String bookingCode;
    private String thumbnail;
    private BookingStatus bookingStatus;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    private String address;
    private int numberOfPerson;
    private BigDecimal totalPrice;
}
