package com.threektechone.resorthub.mapper;

import java.util.Map;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.threektechone.resorthub.common.exception.custom.InvalidEditRequestDataException;
import com.threektechone.resorthub.dto.OwnerModuleDTO.EditRequestDTO;
import com.threektechone.resorthub.dto.StaffModuleDTO.EditResponseDetailDTO;
import com.threektechone.resorthub.dto.StaffModuleDTO.EditResponseListDTO;
import com.threektechone.resorthub.models.EditResortRequest;
import com.fasterxml.jackson.core.type.TypeReference;

@Mapper(componentModel = "spring")
public interface EditRequestMapper {
   @Mapping(target = "requestStatus", constant = "PENDING")
   @Mapping(target = "newData", source = "newData")
   @BeanMapping(ignoreByDefault = true)
   EditResortRequest toRequest(EditRequestDTO dto);
   
   @Mapping(target = "requestId",  source="requestId")
   @Mapping(target = "resortName", source = "resort.name")
   @Mapping(target = "ownerEmail", source = "createdBy.email")
   @Mapping(target = "createdAt", source = "createAt")
   EditResponseListDTO toEditResponseListDTO(EditResortRequest request);
   
   @Mapping(target = "requestId",  source="requestId")
   @Mapping(target = "resortId", source = "request.resort.resortId")
   @Mapping(target = "oldData", source = "oldData")
   @Mapping(target = "newData", source = "newData")
   EditResponseDetailDTO toEditResponseDetailDTO(EditResortRequest request);

   ObjectMapper objectMapper = new ObjectMapper();

   default Map<String, Object> jsonToMap(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {});
        } catch (JsonProcessingException  e) {
            throw new InvalidEditRequestDataException("Failed to deserialize edit request JSON data",e);
        }
    }

   default String map(Map<String, Object> value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException  e) {
            throw new InvalidEditRequestDataException("Failed to serialize edit request JSON data",e);
        }
    }

}
