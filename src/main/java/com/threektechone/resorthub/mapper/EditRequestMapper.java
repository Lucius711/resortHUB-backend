package com.threektechone.resorthub.mapper;

import org.springframework.stereotype.Component;

import com.threektechone.resorthub.dto.OwnerModuleDTO.EditRequestDTO;
import com.threektechone.resorthub.enums.RequestStatus;
import com.threektechone.resorthub.models.EditResortRequest;

@Component
public class EditRequestMapper {
     public EditResortRequest toRequest(EditRequestDTO dto) {
        EditResortRequest request = new EditResortRequest();
        request.setRequestStatus(RequestStatus.PENDING);
        return request;
     }
}
