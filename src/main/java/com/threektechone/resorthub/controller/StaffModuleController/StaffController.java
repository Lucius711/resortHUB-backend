package com.threektechone.resorthub.controller.StaffModuleController;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.threektechone.resorthub.common.response.ApiResponse;
import com.threektechone.resorthub.dto.StaffModuleDTO.EditRequestDecisionDTO;
import com.threektechone.resorthub.dto.StaffModuleDTO.EditResponseDetailDTO;
import com.threektechone.resorthub.dto.StaffModuleDTO.EditResponseListDTO;
import com.threektechone.resorthub.dto.StaffModuleDTO.RegisterRequestDecisionDTO;
import com.threektechone.resorthub.dto.StaffModuleDTO.RegisterResponseDetailDTO;
import com.threektechone.resorthub.dto.StaffModuleDTO.RegisterResponseListDTO;
import com.threektechone.resorthub.enums.ContractType;
import com.threektechone.resorthub.enums.RequestStatus;
import com.threektechone.resorthub.enums.ResortStatus;
import com.threektechone.resorthub.service.StaffModule.ReviewService;

import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/api/staff")
@RequiredArgsConstructor
public class StaffController {
    private final ReviewService reviewService;

    @GetMapping("/register-requests")
    public ResponseEntity<ApiResponse<Page<RegisterResponseListDTO>>> getRegisterRequests(@RequestParam(required=false) String searchkey,@RequestParam(required=false) ResortStatus status,@PageableDefault(size=5) Pageable pageable) {
        Page<RegisterResponseListDTO> dtoList = reviewService.getAllRegisterResort(searchkey, status, pageable);

        ApiResponse<Page<RegisterResponseListDTO>> response = new ApiResponse<>(200,null,dtoList,LocalDateTime.now());
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/register-requests/{id}")
    public ResponseEntity<ApiResponse<RegisterResponseDetailDTO>> getRegisterDetail(@PathVariable int id) {
        RegisterResponseDetailDTO dto = reviewService.getRegisterDetail(id);
        
        ApiResponse<RegisterResponseDetailDTO> response = new ApiResponse<>(200,null,dto,LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register-requests/{id}/review")
    public ResponseEntity<ApiResponse<String>> reviewRegisterRequest(@PathVariable int id,@RequestBody RegisterRequestDecisionDTO dto,Authentication authentication) {
        String email = authentication.getName();

        reviewService.reviewRegisterRequest(dto,id,email);
        
        ApiResponse<String> response = new ApiResponse<>(200,null,"Review successfully!",LocalDateTime.now());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register-requests/{id}/send-contract")
    public ResponseEntity<ApiResponse<String>> sendContract(@PathVariable int id,@RequestParam("file") MultipartFile file,Authentication authentication,ContractType type) throws IOException {

        String email = authentication.getName();

        reviewService.sendContract(id, file, email, type);
        
        ApiResponse<String> response = new ApiResponse<>(200,null,"Send contract successfully!",LocalDateTime.now());

        return ResponseEntity.ok(response);
    }
    
    
     
    @GetMapping("/edit-requests")
    public ResponseEntity<ApiResponse<Page<EditResponseListDTO>>> getEditRequest(@RequestParam(required=false) RequestStatus status,@PageableDefault(size=5) Pageable pageable) {
        Page<EditResponseListDTO> dtoList = reviewService.getEditRequests(status, pageable);
        ApiResponse<Page<EditResponseListDTO>> response = new ApiResponse<>(200,null,dtoList,LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/edit-requests/{id}")
    public ResponseEntity<ApiResponse<EditResponseDetailDTO>> getRequestDetail(@PathVariable int id) {
        EditResponseDetailDTO dto = reviewService.getRequestDetail(id);

        ApiResponse<EditResponseDetailDTO> response = new ApiResponse<>(200,null,dto,LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/edit-requests/{id}/review")
    public ResponseEntity<ApiResponse<String>> reviewEditRequest(@PathVariable int id,@RequestBody EditRequestDecisionDTO dto) {
        
        reviewService.reviewEditRequest(dto,id);
        
        ApiResponse<String> response = new ApiResponse<>(200,null,"Review successfully!",LocalDateTime.now());

        return ResponseEntity.ok(response);
    }
    
    


    
}
