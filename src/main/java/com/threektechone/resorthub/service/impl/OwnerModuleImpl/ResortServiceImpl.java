package com.threektechone.resorthub.service.impl.OwnerModuleImpl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.threektechone.resorthub.ExceptionHandler.CustomException.ResourceNotFoundException;
import com.threektechone.resorthub.dto.OwnerModuleDTO.EditRequestDTO;
import com.threektechone.resorthub.dto.OwnerModuleDTO.OwnerResortsResponseDTO;
import com.threektechone.resorthub.enums.ResortStatus;
import com.threektechone.resorthub.mapper.EditRequestMapper;
import com.threektechone.resorthub.mapper.ResortMapper;
import com.threektechone.resorthub.models.EditResortRequest;
import com.threektechone.resorthub.models.Resort;
import com.threektechone.resorthub.models.ResortAmenity;
import com.threektechone.resorthub.models.ResortImage;
import com.threektechone.resorthub.models.ResortMenu;
import com.threektechone.resorthub.models.User;
import com.threektechone.resorthub.repositories.EditResortRequestRepository;
import com.threektechone.resorthub.repositories.ResortRepository;
import com.threektechone.resorthub.repositories.UserRepository;
import com.threektechone.resorthub.service.OwnerModule.ResortService;

import lombok.RequiredArgsConstructor;
import tools.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class ResortServiceImpl implements ResortService {

    private final ResortRepository resortRepository;

    private final ResortMapper resortMapper;

    private final UserRepository userRepository;

    private final ObjectMapper objectMapper;

    private final EditResortRequestRepository editResortRequestRepository;

    private final EditRequestMapper editRequestMapper;

    private Map<String, Object> getOldData(Resort resort, Map<String, Object> newData) {
        Map<String, Object> oldData = new HashMap<>();
        for (String key : newData.keySet()) {
        switch (key) {
            case "name" -> oldData.put("name", resort.getName());

            case "location" -> oldData.put("location", resort.getLocation());

            case "description" -> oldData.put("description", resort.getDescription());

            case "maxGuest" -> oldData.put("maxGuest",resort.getMaxGuest());

            case "averageRating" -> oldData.put("averageRating", resort.getAverageRating());

            case "amenities" -> oldData.put("amenities",resort.getAmenities().stream()
            .map(ResortAmenity::getAmenityId).toList());

            case "menuItems" -> oldData.put("menuItems",resort.getMenuItems().stream()
            .map(ResortMenu::getMenuId).toList());

            case "images" -> oldData.put("images",resort.getImages().stream()
            .map(ResortImage::getImageId).toList());

            case "price" -> oldData.put("price",resort.getPrice());
    
        }
    }
    return oldData;
}

    @Override
    public Page<OwnerResortsResponseDTO> getAllOwnerResorts(String email,String searchkey, ResortStatus status, Pageable pageable) {
        Page<Resort> resortList = resortRepository.getOwnerResorts(email,searchkey, status, pageable);
        
        return resortList.map(resortMapper::toOwnerResortList);
    }

    @Override
    public void createEditRequest(EditRequestDTO dto, String email) {
        var resort = resortRepository.findById(dto.getResortId())
        .orElseThrow(() -> new ResourceNotFoundException("Resort not found!"));

        User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        Map<String, Object> oldData = getOldData(resort, dto.getNewData());

        EditResortRequest request = editRequestMapper.toRequest(dto);
        request.setResort(resort);
        request.setOldData(objectMapper.writeValueAsString(oldData));
        request.setNewData(objectMapper.writeValueAsString(dto.getNewData()));
        request.setCreatedBy(user);
        request.setApprovedBy(resort.getStaff());

        editResortRequestRepository.save(request);
    }
    
}
