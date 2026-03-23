package com.threektechone.resorthub.dto.customer;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookingRequestDTO {
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    private int numberOfPerson;
    private List<MealSelectionRequestDTO> meals;
}
