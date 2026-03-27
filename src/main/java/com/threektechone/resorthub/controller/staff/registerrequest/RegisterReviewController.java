package com.threektechone.resorthub.controller.staff.registerrequest;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.threektechone.resorthub.common.response.ApiResponse;
import com.threektechone.resorthub.dto.staff.RegisterRequestDecisionDTO;
import com.threektechone.resorthub.enums.ContractType;
import com.threektechone.resorthub.service.staff.ReviewService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/staff/register-requests")
@RequiredArgsConstructor
public class RegisterReviewController {

    private final ReviewService reviewService;

    @PostMapping("/{requestId}/review")
    public ResponseEntity<ApiResponse<String>> review(
        @PathVariable int requestId,
        @RequestBody RegisterRequestDecisionDTO dto,
        Authentication authentication
    ) {
        reviewService.reviewRegisterRequest(dto, requestId, authentication.getName());

        return ResponseEntity.ok(
            new ApiResponse<>(200,null,"Review successfully!",LocalDateTime.now())
        );
    }

    @PostMapping("/{requestId}/send-contract")
    public ResponseEntity<ApiResponse<String>> sendContract(
        @PathVariable int requestId,
        @RequestParam("file") MultipartFile file,
        Authentication authentication,
        ContractType type
    ) throws IOException {

        reviewService.sendContract(requestId, file, authentication.getName(), type);

        return ResponseEntity.ok(
            new ApiResponse<>(200,null,"Send contract successfully!",LocalDateTime.now())
        );
    }
}