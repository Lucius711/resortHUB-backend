package com.threektechone.resorthub.dto.AdminModuleDTO;

import com.threektechone.resorthub.enums.UserStatus;

public class UpdateStatusRequestDTO {
    private UserStatus status;

    public UpdateStatusRequestDTO() {};

    public UpdateStatusRequestDTO(UserStatus status) {
        this.status = status;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }
    
}
