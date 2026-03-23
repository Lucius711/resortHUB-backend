package com.threektechone.resorthub.dto.staff;

import com.threektechone.resorthub.enums.ResortStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RegisterResponseListDTO {
    private int resortId;
    private String resortCode;
    private String resortName;
    private String ownerName;
    private String ownerPhone;
    private ResortStatus resortStatus;
    
}
