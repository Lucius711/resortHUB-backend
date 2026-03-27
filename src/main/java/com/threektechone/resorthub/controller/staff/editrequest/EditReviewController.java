package com.threektechone.resorthub.controller.staff.editrequest;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.threektechone.resorthub.common.response.ApiResponse;
import com.threektechone.resorthub.dto.staff.EditRequestDecisionDTO;
import com.threektechone.resorthub.service.staff.ReviewService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/staff/edit-requests")
@RequiredArgsConstructor
public class EditReviewController {

    private final ReviewService reviewService;

    @PostMapping("/{requestId}/review")
    public ResponseEntity<ApiResponse<String>> reviewEditRequest(
        @PathVariable int requestId,
        @RequestBody EditRequestDecisionDTO dto,
        Authentication authentication
    ) {
        reviewService.reviewEditRequest(dto, requestId, authentication.getName());

        return ResponseEntity.ok(
            new ApiResponse<>(200,null,"Review successfully!",LocalDateTime.now())
        );
    }
}
