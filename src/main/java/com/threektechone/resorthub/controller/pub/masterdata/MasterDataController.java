package com.threektechone.resorthub.controller.pub.masterdata;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.threektechone.resorthub.common.response.ApiResponse;
import com.threektechone.resorthub.models.Province;
import com.threektechone.resorthub.models.Ward;
import com.threektechone.resorthub.service.masterdata.MasterDataService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MasterDataController {

    private final MasterDataService masterDataService;

    @GetMapping("/provinces")
    public ResponseEntity<ApiResponse<List<Province>>> getProvinces() {
        List<Province> provinceList = masterDataService.getProvinces();
        ApiResponse<List<Province>> response = new ApiResponse<>(200, null, provinceList, LocalDateTime.now());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/provinces/{id}/wards")
    public ResponseEntity<ApiResponse<List<Ward>>> getWards(@PathVariable Integer id) {
        List<Ward> wardList = masterDataService.getWardsByProvince(id);

        ApiResponse<List<Ward>> response = new ApiResponse<>(200, null, wardList, LocalDateTime.now());

        return ResponseEntity.ok(response);
    }

}
