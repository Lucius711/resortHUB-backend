package com.threektechone.resorthub.service.OwnerModule;

import com.threektechone.resorthub.dto.OwnerModuleDTO.EditRequestDTO;

public interface  EditResortRequestService {
    void createEditRequest(EditRequestDTO dto,String email);
}
