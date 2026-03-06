package com.threektechone.resorthub.service.StaffModule;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.threektechone.resorthub.dto.StaffModuleDTO.EditRequestDetailDTO;
import com.threektechone.resorthub.dto.StaffModuleDTO.EditRequestListDTO;
import com.threektechone.resorthub.dto.StaffModuleDTO.EditResponseViewDTO;
import com.threektechone.resorthub.enums.RequestStatus;

public interface ReviewService {
    void reviewEditRequest(EditResponseViewDTO dto);
    Page<EditRequestListDTO> getEditRequests(RequestStatus status,Pageable pageable);
    EditRequestDetailDTO getRequestDetail(int requestId);
}
