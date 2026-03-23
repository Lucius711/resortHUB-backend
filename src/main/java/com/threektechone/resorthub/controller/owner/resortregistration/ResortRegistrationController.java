package com.threektechone.resorthub.controller.owner.resortregistration;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.threektechone.resorthub.common.response.ApiResponse;
import com.threektechone.resorthub.dto.owner.RegisterAmenitiesRequestDTO;
import com.threektechone.resorthub.dto.owner.RegisterBasicInfoRequestDTO;
import com.threektechone.resorthub.dto.owner.RegisterCapacityPricingRequestDTO;
import com.threektechone.resorthub.dto.owner.RegisterImagesRequestDTO;
import com.threektechone.resorthub.dto.owner.RegisterMenusRequestDTO;
import com.threektechone.resorthub.service.owner.ResortService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/owner/register-resort")
@RequiredArgsConstructor
public class ResortRegistrationController {

    private final ResortService resortService;

    @PostMapping
    public ResponseEntity<ApiResponse<Integer>> create(Authentication authentication) {
        int id = resortService.createRegistrationResort(authentication.getName());

        return ResponseEntity.status(201)
            .body(new ApiResponse<>(201,null,id,LocalDateTime.now()));
    }

    @PatchMapping("/{id}/basic-info")
    public ResponseEntity<ApiResponse<String>> basicInfo(
        @RequestBody RegisterBasicInfoRequestDTO dto,
        @PathVariable int id,
        Authentication auth
    ) {
        resortService.updateBasicInfoResort(dto, id, auth.getName());

        return ResponseEntity.ok(
            new ApiResponse<>(200,null,"Update basic info successfully!",LocalDateTime.now())
        );
    }

    @PatchMapping("/{id}/capacity-price")
    public ResponseEntity<ApiResponse<String>> capacityPrice(
        @RequestBody RegisterCapacityPricingRequestDTO dto,
        @PathVariable int id,
        Authentication auth
    ) {
        resortService.updateCapacityPriceResort(dto, id, auth.getName());

        return ResponseEntity.ok(
            new ApiResponse<>(200,null,"Update capacity and price successfully!",LocalDateTime.now())
        );
    }

    @PatchMapping("/{id}/amenities")
    public ResponseEntity<ApiResponse<String>> amenities(
        @RequestBody RegisterAmenitiesRequestDTO dto,
        @PathVariable int id,
        Authentication auth
    ) {
        resortService.updateAmenitiesResort(dto, id, auth.getName());

        return ResponseEntity.ok(
            new ApiResponse<>(200,null,"Update amenities successfully!",LocalDateTime.now())
        );
    }

    @PatchMapping("/{id}/images")
    public ResponseEntity<ApiResponse<String>> images(
        @RequestBody RegisterImagesRequestDTO dto,
        @PathVariable int id,
        Authentication auth
    ) {
        resortService.updateImagesResort(dto, id, auth.getName());

        return ResponseEntity.ok(
            new ApiResponse<>(200,null,"Update images successfully!",LocalDateTime.now())
        );
    }

    @PatchMapping("/{id}/menus")
    public ResponseEntity<ApiResponse<String>> menus(
        @RequestBody RegisterMenusRequestDTO dto,
        @PathVariable int id,
        Authentication auth
    ) {
        resortService.updateMenusResort(dto, id, auth.getName());

        return ResponseEntity.ok(
            new ApiResponse<>(200,null,"Update menus successfully!",LocalDateTime.now())
        );
    }

    @PostMapping("/{id}/submit")
    public ResponseEntity<ApiResponse<String>> submit(
        @PathVariable int id,
        Authentication auth
    ) {
        resortService.submitRegisterResort(id, auth.getName());

        return ResponseEntity.ok(
            new ApiResponse<>(200,null,"Submit successfully!",LocalDateTime.now())
        );
    }

    @PostMapping("/{id}/sign-contract")
    public ResponseEntity<ApiResponse<String>> signContract(
        @PathVariable int id,
        @RequestParam("file") MultipartFile file,
        @RequestParam Boolean acceptedTerms,
        Authentication auth
    ) throws IOException {

        resortService.signContract(id, file, acceptedTerms, auth.getName());

        return ResponseEntity.ok(
            new ApiResponse<>(200,null,"Sign contract successfully!",LocalDateTime.now())
        );
    }
}