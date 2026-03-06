package com.threektechone.resorthub.controller.StaffModuleController;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.threektechone.resorthub.common.response.ApiResponse;
import com.threektechone.resorthub.dto.StaffModuleDTO.EditRequestDetailDTO;
import com.threektechone.resorthub.dto.StaffModuleDTO.EditRequestListDTO;
import com.threektechone.resorthub.dto.StaffModuleDTO.EditResponseViewDTO;
import com.threektechone.resorthub.enums.RequestStatus;
import com.threektechone.resorthub.service.StaffModule.ReviewService;

import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/api/staff")
@RequiredArgsConstructor
public class StaffController {
    private final ReviewService reviewService;
     
    @GetMapping("/edit-requests")
    public ResponseEntity<ApiResponse<Page<EditRequestListDTO>>> getEditRequest(@RequestParam(required=false) RequestStatus status,@PageableDefault(size=5) Pageable pageable) {
        Page<EditRequestListDTO> dtoList = reviewService.getEditRequests(status, pageable);
        ApiResponse<Page<EditRequestListDTO>> response = new ApiResponse<>(200,null,dtoList,LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/edit-requests/{id}")
    public ResponseEntity<ApiResponse<EditRequestDetailDTO>> getRequestDetail(@PathVariable int id) {
        EditRequestDetailDTO dto = reviewService.getRequestDetail(id);

        ApiResponse<EditRequestDetailDTO> response = new ApiResponse<>(200,null,dto,LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/edit-requests/{id}/review")
    public ResponseEntity<ApiResponse<String>> reviewRequest(@PathVariable int id,@RequestBody EditResponseViewDTO dto) {
        
        reviewService.reviewEditRequest(dto);
        
        ApiResponse<String> response = new ApiResponse<>(200,null,"Review successfully!",LocalDateTime.now());

        return ResponseEntity.ok(response);
    }
    
    


    
}
