package com.threektechone.resorthub.controller.OwnerModuleController;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.threektechone.resorthub.dto.OwnerModuleDTO.OwnerResortsResponseDTO;
import com.threektechone.resorthub.enums.ResortStatus;
import com.threektechone.resorthub.service.OwnerModule.ResortService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/owner")
@RequiredArgsConstructor
public class OwnerController {

    private final ResortService resortService;
     
    @GetMapping("/resorts")
    public ResponseEntity<Page<OwnerResortsResponseDTO>> getAllOwnerResorst(
        @RequestParam(required=false) String searchkey,
        @RequestParam(required=false)  ResortStatus status,
        @PageableDefault(size=5) Pageable pageable,
        Authentication authentication) {
        String email = authentication.getName();
          
        Page<OwnerResortsResponseDTO> resortList = resortService.getAllOwnerResorts(email,searchkey, status, pageable);
        return ResponseEntity.ok(resortList);
    }
    

}