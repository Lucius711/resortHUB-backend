package com.threektechone.resorthub.dto.owner;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EditRequestDTO {
    private int resortId;

    private Map<String, Object> newData;
    
}
