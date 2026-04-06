package com.threektechone.resorthub.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PeriodDTO {
    private int current;
    private int previous;
    private double percentage;
}
