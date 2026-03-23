package com.threektechone.resorthub.service.staff;


import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.threektechone.resorthub.dto.staff.EditRequestDecisionDTO;
import com.threektechone.resorthub.dto.staff.EditResponseDetailDTO;
import com.threektechone.resorthub.dto.staff.EditResponseListDTO;
import com.threektechone.resorthub.dto.staff.RegisterRequestDecisionDTO;
import com.threektechone.resorthub.dto.staff.RegisterResponseDetailDTO;
import com.threektechone.resorthub.dto.staff.RegisterResponseListDTO;
import com.threektechone.resorthub.enums.ContractType;
import com.threektechone.resorthub.enums.RequestStatus;
import com.threektechone.resorthub.enums.ResortStatus;

public interface ReviewService {
    void reviewEditRequest(EditRequestDecisionDTO dto,int requestId,String staffEmail);
    Page<EditResponseListDTO> getEditRequests(RequestStatus status,Pageable pageable);
    EditResponseDetailDTO getRequestDetail(int requestId);
    Page<RegisterResponseListDTO> getAllRegisterResort(String searchkey,ResortStatus status,Pageable pageable);
    RegisterResponseDetailDTO getRegisterDetail(int resortId);
    void reviewRegisterRequest(RegisterRequestDecisionDTO dto,int resortId,String email);
    void sendContract(int resortId, MultipartFile file,String email,ContractType type) throws IOException;
}
