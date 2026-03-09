package com.threektechone.resorthub.dto.StaffModuleDTO;

import com.threektechone.resorthub.enums.ReviewAction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EditRequestDecisionDTO {
    private int requestId;
    private ReviewAction action;
    private String note;
}
