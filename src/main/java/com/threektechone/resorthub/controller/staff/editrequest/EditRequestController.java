package com.threektechone.resorthub.controller.staff.editrequest;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.threektechone.resorthub.common.response.ApiResponse;
import com.threektechone.resorthub.dto.staff.EditResponseDetailDTO;
import com.threektechone.resorthub.dto.staff.EditResponseListDTO;
import com.threektechone.resorthub.enums.RequestStatus;
import com.threektechone.resorthub.service.staff.ReviewService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/staff/edit-requests")
@RequiredArgsConstructor
public class EditRequestController {

    private final ReviewService reviewService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<EditResponseListDTO>>> getAll(
        @RequestParam(required=false) RequestStatus status,
        @PageableDefault(size=5) Pageable pageable
    ) {
        return ResponseEntity.ok(
            new ApiResponse<>(200,null,
                reviewService.getEditRequests(status, pageable),
                LocalDateTime.now())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EditResponseDetailDTO>> getDetail(@PathVariable int id) {
        return ResponseEntity.ok(
            new ApiResponse<>(200,null,
                reviewService.getRequestDetail(id),
                LocalDateTime.now())
        );
    }
}
