package com.threektechone.resorthub.controller.staff.registerrequest;

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
import com.threektechone.resorthub.dto.staff.RegisterResponseDetailDTO;
import com.threektechone.resorthub.dto.staff.RegisterResponseListDTO;
import com.threektechone.resorthub.enums.ResortStatus;
import com.threektechone.resorthub.service.staff.ReviewService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/staff/register-requests")
@RequiredArgsConstructor
public class RegisterRequestController {

    private final ReviewService reviewService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<RegisterResponseListDTO>>> getAll(
        @RequestParam(required=false) String searchkey,
        @RequestParam(required=false) ResortStatus status,
        @PageableDefault(size=5) Pageable pageable
    ) {
        Page<RegisterResponseListDTO> dtoList =
            reviewService.getAllRegisterResort(searchkey, status, pageable);

        return ResponseEntity.ok(new ApiResponse<>(200,null,dtoList,LocalDateTime.now()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RegisterResponseDetailDTO>> getDetail(@PathVariable int id) {
        return ResponseEntity.ok(
            new ApiResponse<>(200,null,reviewService.getRegisterDetail(id),LocalDateTime.now())
        );
    }
}
