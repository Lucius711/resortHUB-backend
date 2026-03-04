package com.threektechone.resorthub.controller.OwnerModuleController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.threektechone.resorthub.dto.OwnerModuleDTO.OwnerResortsResponseDTO;
import com.threektechone.resorthub.enums.ResortStatus;
import com.threektechone.resorthub.service.OwnerModule.ResortService;


@RestController
@RequestMapping("/api/owner")
public class OwnerController {

    @Autowired
    private ResortService resortService;
     
    @GetMapping("/resorts")
    public Page<OwnerResortsResponseDTO> getAllOwnerResorst(@RequestParam(required=false) String searchkey,@RequestParam(required=false)  ResortStatus status,@PageableDefault(size=5) Pageable pageable,Authentication authentication) {
        String email = authentication.getName();
          
        Page<OwnerResortsResponseDTO> resortList = resortService.getAllOwnerResorts(email,searchkey, status, pageable);
        return resortList;
    }
    

}