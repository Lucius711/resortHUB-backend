package com.threektechone.resorthub.controller.owner.resort;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.threektechone.resorthub.common.response.ApiResponse;
import com.threektechone.resorthub.dto.owner.OwnerResortsDetailResponseDTO;
import com.threektechone.resorthub.dto.owner.OwnerResortsListResponseDTO;
import com.threektechone.resorthub.enums.ResortStatus;
import com.threektechone.resorthub.service.owner.ResortService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/owner/resorts")
@RequiredArgsConstructor
public class OwnerResortController {

    private final ResortService resortService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<OwnerResortsListResponseDTO>>> getOwnerResorts(
        @RequestParam(required=false) String searchkey,
        @RequestParam(required=false) ResortStatus status,
        @PageableDefault(size=5) Pageable pageable,
        Authentication authentication
    ) {
        String email = authentication.getName();

        Page<OwnerResortsListResponseDTO> resortList =
                resortService.getAllOwnerResorts(email, searchkey, status, pageable);

        return ResponseEntity.ok(
            new ApiResponse<>(200,null,resortList,LocalDateTime.now())
        );
    }

    @GetMapping("/{resortId}")
    public ResponseEntity<ApiResponse<OwnerResortsDetailResponseDTO>> getResortDetail(
        @PathVariable int resortId,
        Authentication authentication) {
        
            String email = authentication.getName();
            OwnerResortsDetailResponseDTO dto = resortService.getOwnerResortsDetail(resortId, email);
            ApiResponse<OwnerResortsDetailResponseDTO> response = new ApiResponse<>(200,null,dto,LocalDateTime.now());

            return ResponseEntity.ok(response);
    }

    

    
}