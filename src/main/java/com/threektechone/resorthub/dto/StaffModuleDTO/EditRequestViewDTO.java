package com.threektechone.resorthub.dto.StaffModuleDTO;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EditRequestViewDTO {
    private Long requestId;
    private int resortId;
    private Map<String,Object> oldData;
    private Map<String,Object> newData;
}
