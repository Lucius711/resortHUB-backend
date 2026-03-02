package com.threektechone.resorthub.dto.AdminModuleDTO;

import com.threektechone.resorthub.enums.RoleName;

public class UpdateRoleRequestDTO {
    private RoleName roleName;

    public UpdateRoleRequestDTO() {};

    public UpdateRoleRequestDTO(RoleName roleName) {
        this.roleName = roleName;
    }

    public RoleName getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }
}
