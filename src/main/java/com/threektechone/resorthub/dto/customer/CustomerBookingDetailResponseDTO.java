package com.threektechone.resorthub.dto.customer;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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
public class CustomerBookingDetailResponseDTO {
    private String resortName;
    private String resortCode;
    private ResortType resortType;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    private int numberOfPerson;
    private String ownerImage;
    private String ownerName;
    private List<MealSelectionRequestDTO> meals;
    private BigDecimal mealPrice;
    private BigDecimal totalPrice;
    private PaymentStatus paymentStatus;
}
