package com.threektechone.resorthub.dto.OwnerModuleDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.threektechone.resorthub.dto.CustomerModuleDTO.MealSelectionRequestDTO;
import com.threektechone.resorthub.enums.BookingStatus;
import com.threektechone.resorthub.enums.PaymentStatus;
import com.threektechone.resorthub.enums.ResortType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OwnerBookingDetailResponseDTO {
    private String resortName;
    private String resortCode;
    private ResortType resortType;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    private int numberOfPerson;
    private String customerImage;
    private String customerName;
    private List<MealSelectionRequestDTO> meals;
    private BigDecimal mealPrice;
    private BigDecimal totalPrice;
    private PaymentStatus paymentStatus;
    private BookingStatus bookingStatus;
    private Boolean roomAvailable;
    private Boolean canCheckIn;
    private Boolean canCheckOut;
}
