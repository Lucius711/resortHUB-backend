package com.threektechone.resorthub.service.StaffModule;


import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.threektechone.resorthub.dto.StaffModuleDTO.EditRequestDecisionDTO;
import com.threektechone.resorthub.dto.StaffModuleDTO.EditResponseDetailDTO;
import com.threektechone.resorthub.dto.StaffModuleDTO.EditResponseListDTO;
import com.threektechone.resorthub.dto.StaffModuleDTO.RegisterRequestDecisionDTO;
import com.threektechone.resorthub.dto.StaffModuleDTO.RegisterResponseDetailDTO;
import com.threektechone.resorthub.dto.StaffModuleDTO.RegisterResponseListDTO;
import com.threektechone.resorthub.enums.ContractType;
import com.threektechone.resorthub.enums.RequestStatus;
import com.threektechone.resorthub.enums.ResortStatus;

public interface ReviewService {
    void reviewEditRequest(EditRequestDecisionDTO dto,int requestId);
    Page<EditResponseListDTO> getEditRequests(RequestStatus status,Pageable pageable);
    EditResponseDetailDTO getRequestDetail(int requestId);
    Page<RegisterResponseListDTO> getAllRegisterResort(String searchkey,ResortStatus status,Pageable pageable);
    RegisterResponseDetailDTO getRegisterDetail(int resortId);
    void reviewRegisterRequest(RegisterRequestDecisionDTO dto,int resortId,String email);
    void sendContract(int resortId, MultipartFile file,String email,ContractType type) throws IOException;
}
