package com.threektechone.resorthub.mapper;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.threektechone.resorthub.dto.OwnerModuleDTO.EditRequestDTO;
import com.threektechone.resorthub.dto.StaffModuleDTO.EditRequestDetailDTO;
import com.threektechone.resorthub.dto.StaffModuleDTO.EditRequestListDTO;
import com.threektechone.resorthub.enums.RequestStatus;
import com.threektechone.resorthub.models.EditResortRequest;

import lombok.RequiredArgsConstructor;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
public class EditRequestMapper {
   private final ObjectMapper objectMapper;

     public EditResortRequest toRequest(EditRequestDTO dto) {
        EditResortRequest request = new EditResortRequest();
        request.setRequestStatus(RequestStatus.PENDING);
        return request;
     }

     public EditRequestListDTO toEditRequestListDTO(EditResortRequest request) {
        EditRequestListDTO dto = new EditRequestListDTO();
        dto.setRequestId(request.getRequestId());
        dto.setResortName(request.getResort().getName());
        dto.setOwnerEmail(request.getCreatedBy().getEmail());
        dto.setCreatedAt(request.getCreateAt());
        return dto;
     }

     public EditRequestDetailDTO toEditRequestDetailDTO(EditResortRequest request) {
        EditRequestDetailDTO dto = new EditRequestDetailDTO();
        dto.setRequestId(request.getRequestId());
        
        Map<String,Object> oldData =objectMapper.readValue(request.getOldData(), Map.class);
        dto.setOldData(oldData);

        Map<String,Object> newData =objectMapper.readValue(request.getNewData(), Map.class);
        dto.setNewData(newData);

        return dto;
     }
}
