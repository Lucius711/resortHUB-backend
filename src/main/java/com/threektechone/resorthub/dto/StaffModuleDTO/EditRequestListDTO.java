package com.threektechone.resorthub.dto.StaffModuleDTO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EditRequestListDTO {
    private int requestId;
    private String resortName;
    private String ownerEmail;
    private LocalDateTime createdAt;
}
