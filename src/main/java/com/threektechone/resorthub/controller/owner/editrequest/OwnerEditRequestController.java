package com.threektechone.resorthub.controller.owner.editrequest;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.threektechone.resorthub.common.response.ApiResponse;
import com.threektechone.resorthub.dto.owner.EditRequestDTO;
import com.threektechone.resorthub.service.owner.ResortService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/owner/edit-request")
@RequiredArgsConstructor
public class OwnerEditRequestController {

    private final ResortService resortService;

    @PostMapping
    public ResponseEntity<ApiResponse<String>> create(
        @RequestBody EditRequestDTO dto,
        Authentication authentication
    ) {
        resortService.createEditRequest(dto, authentication.getName());

        return ResponseEntity.status(201)
            .body(new ApiResponse<>(201,null,"Send request successfully!",LocalDateTime.now()));
    }
}