package com.threektechone.resorthub.dto.OwnerModuleDTO;

import com.threektechone.resorthub.enums.ReviewAction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BookingRequestDecisionDTO {
    private ReviewAction action;
    private String reason;
}
