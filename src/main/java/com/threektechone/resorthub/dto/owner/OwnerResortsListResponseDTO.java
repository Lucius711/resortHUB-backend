package com.threektechone.resorthub.dto.owner;

import java.time.LocalDateTime;

import com.threektechone.resorthub.enums.ResortStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OwnerResortsListResponseDTO {
    private int resortId;
    private String resortName;
    private String approvedByName;
    private String approvedByPhone;
    private ResortStatus resortStatus;
    private String city;
    private String district;
    private String address;
    private LocalDateTime createdAt;
    private int completedSteps;
    private int totalSteps;
}
