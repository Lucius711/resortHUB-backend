package com.threektechone.resorthub.dto.admin;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserChartDTO {
    private LocalDate date;
    private int count;
}
