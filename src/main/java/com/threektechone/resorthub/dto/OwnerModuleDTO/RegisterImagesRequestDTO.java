package com.threektechone.resorthub.dto.OwnerModuleDTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RegisterImagesRequestDTO {
    private List<Integer> imageIds;
}
