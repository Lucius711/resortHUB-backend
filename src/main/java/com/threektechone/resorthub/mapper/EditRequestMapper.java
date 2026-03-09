package com.threektechone.resorthub.mapper;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.threektechone.resorthub.dto.OwnerModuleDTO.EditRequestDTO;
import com.threektechone.resorthub.dto.StaffModuleDTO.EditResponseDetailDTO;
import com.threektechone.resorthub.dto.StaffModuleDTO.EditResponseListDTO;
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

     public EditResponseListDTO toEditResponseListDTO(EditResortRequest request) {
        EditResponseListDTO dto = new EditResponseListDTO();
        dto.setRequestId(request.getRequestId());
        dto.setResortName(request.getResort().getName());
        dto.setOwnerEmail(request.getCreatedBy().getEmail());
        dto.setCreatedAt(request.getCreateAt());
        return dto;
     }

     public EditResponseDetailDTO toEditResponseDetailDTO(EditResortRequest request) {
        EditResponseDetailDTO dto = new EditResponseDetailDTO();
        dto.setRequestId(request.getRequestId());
        
        Map<String,Object> oldData =objectMapper.readValue(request.getOldData(), Map.class);
        dto.setOldData(oldData);

        Map<String,Object> newData =objectMapper.readValue(request.getNewData(), Map.class);
        dto.setNewData(newData);

        return dto;
     }
}
