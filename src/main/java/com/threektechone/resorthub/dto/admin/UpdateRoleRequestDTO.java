package com.threektechone.resorthub.dto.admin;

import com.threektechone.resorthub.enums.RoleName;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UpdateRoleRequestDTO {
    private RoleName roleName;
}
