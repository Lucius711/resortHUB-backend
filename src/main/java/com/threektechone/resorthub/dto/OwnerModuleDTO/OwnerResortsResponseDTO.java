package com.threektechone.resorthub.dto.OwnerModuleDTO;

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
public class OwnerResortsResponseDTO {
    private int resortId;
    private String resortName;
    private String approvedByName;
    private String approvedByPhone;
    private ResortStatus resortStatus;
    private String location;
    private LocalDateTime createdAt;
    
}
