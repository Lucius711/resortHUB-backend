package com.threektechone.resorthub.dto.staff;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResortStatusChartDTO {
    private String status;
    private long count;
}
