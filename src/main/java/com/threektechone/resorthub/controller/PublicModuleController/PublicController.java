package com.threektechone.resorthub.controller.PublicModuleController;

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
import com.threektechone.resorthub.dto.PublicModuleDTO.PublicResortResponseDetailDTO;
import com.threektechone.resorthub.dto.PublicModuleDTO.PublicResortResponseListDTO;
import com.threektechone.resorthub.enums.ResortType;
import com.threektechone.resorthub.service.PublicModule.PublicResortService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PublicController {

    private final PublicResortService resortService;

    @GetMapping("/resorts")
    public ResponseEntity<ApiResponse<Page<PublicResortResponseListDTO>>> getPublicResorts(@RequestParam(required=false) String searchkey,@RequestParam(required=false) String city, @RequestParam(required=false) ResortType type, @PageableDefault(size =5) Pageable pageable) {
        Page<PublicResortResponseListDTO> dtoList = resortService.getPublicResorts(searchkey, city, type, pageable);

        ApiResponse<Page<PublicResortResponseListDTO>> response = new ApiResponse<>(200,null,dtoList,LocalDateTime.now());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/resorts/{id}")
    public ResponseEntity<ApiResponse<PublicResortResponseDetailDTO>> getResortDetails(@PathVariable int id) {
        
        PublicResortResponseDetailDTO dto = resortService.getResortDetail(id);

        ApiResponse<PublicResortResponseDetailDTO> response = new ApiResponse<>(200,null,dto,LocalDateTime.now());
        return ResponseEntity.ok(response);
    }
    
    
    
}
