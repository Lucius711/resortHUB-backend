package com.threektechone.resorthub.service.impl.StaffModuleImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.threektechone.resorthub.ExceptionHandler.CustomException.ResourceNotFoundException;
import com.threektechone.resorthub.dto.StaffModuleDTO.EditRequestDetailDTO;
import com.threektechone.resorthub.dto.StaffModuleDTO.EditRequestListDTO;
import com.threektechone.resorthub.dto.StaffModuleDTO.EditResponseViewDTO;
import com.threektechone.resorthub.dto.StaffModuleDTO.RegisterRequestListDTO;
import com.threektechone.resorthub.enums.RequestStatus;
import com.threektechone.resorthub.enums.ResortStatus;
import com.threektechone.resorthub.enums.ReviewAction;
import com.threektechone.resorthub.mapper.EditRequestMapper;
import com.threektechone.resorthub.mapper.ResortMapper;
import com.threektechone.resorthub.models.EditResortRequest;
import com.threektechone.resorthub.models.Resort;
import com.threektechone.resorthub.models.ResortAmenity;
import com.threektechone.resorthub.models.ResortImage;
import com.threektechone.resorthub.models.ResortMenu;
import com.threektechone.resorthub.repositories.EditResortRequestRepository;
import com.threektechone.resorthub.repositories.ResortAmenityRepository;
import com.threektechone.resorthub.repositories.ResortImageRepository;
import com.threektechone.resorthub.repositories.ResortMenuRepository;
import com.threektechone.resorthub.repositories.ResortRepository;
import com.threektechone.resorthub.service.StaffModule.ReviewService;

import lombok.RequiredArgsConstructor;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final EditResortRequestRepository requestRepository;
    private final ObjectMapper objectMapper;
    private final ResortAmenityRepository resortAmenityRepository;
    private final ResortMenuRepository resortMenuRepository;
    private final ResortImageRepository resortImageRepository;
    private final ResortRepository resortRepository;
    private final EditRequestMapper requestMapper;
    private final ResortMapper resortMapper;
    

    //Get register resort list
    @Override
    public Page<RegisterRequestListDTO> getAllRegisterResort(String searchkey, ResortStatus status, Pageable pageable) {
        Page<Resort> registerList = resortRepository.getRegisterResorts(searchkey, status, pageable);

        return registerList.map(resortMapper::toRegisterRequestListDTO);
    }

    //Get pending request list
    @Override
    public Page<EditRequestListDTO> getEditRequests(RequestStatus status,Pageable pageable) {
        Page<EditResortRequest> requestList = requestRepository.getEditRequests(status, pageable);

        return requestList.map(requestMapper::toEditRequestListDTO);
    }
    
    //View edit request detail
    @Override
    public EditRequestDetailDTO getRequestDetail(int requestId) {
        EditResortRequest request = requestRepository.findById(requestId)
        .orElseThrow(() -> new ResourceNotFoundException("Request not found!"));

        EditRequestDetailDTO dto = requestMapper.toEditRequestDetailDTO(request);
        return dto;
    }
    

    //Handle Edit request
    @Override
    public void reviewEditRequest(EditResponseViewDTO dto) {
        EditResortRequest request = requestRepository.findById(dto.getRequestId())
        .orElseThrow(() -> new ResourceNotFoundException("Request not found!"));

        if (ReviewAction.APPROVE.equals(dto.getAction())) {

            Resort resort = request.getResort();

            Map<String, Object> newData = objectMapper.readValue(
                request.getNewData(),
                new TypeReference<Map<String, Object>>() {}
            );
            
            //Handle update
            if (newData.containsKey("name")) {
                resort.setName((String) newData.get("name"));
            }

            if (newData.containsKey("city")) {
                resort.setCity(((String) newData.get("location")));
            }

            if (newData.containsKey("district")) {
                resort.setDistrict((String) newData.get("district"));
            }

            if (newData.containsKey("address")) {
                resort.setAddress((String) newData.get("address"));
            }

            if (newData.containsKey("description")) {
                resort.setDescription((String) newData.get("description"));
            }

            if (newData.containsKey("maxGuest")) {
                resort.setMaxGuest(Integer.parseInt(newData.get("maxGuest").toString()));
            }

            if (newData.containsKey("averageRating")) {
                resort.setAverageRating(BigDecimal.valueOf(Double.parseDouble(newData.get("averageRating").toString())));
            }

            if (newData.containsKey("price")) {
                resort.setPrice(BigDecimal.valueOf(Double.parseDouble(newData.get("price").toString())));
            }

            if (newData.containsKey("amenities")) {
                List<Integer> amenityIds = (List<Integer>) newData.get("amenities");

                List<ResortAmenity> amenities =resortAmenityRepository.findAllById(amenityIds);

                resort.setAmenities(new HashSet<>(amenities));
            }

            if (newData.containsKey("menuItems")) {
                List<Integer> menuIds = (List<Integer>) newData.get("menuItems");

                List<ResortMenu> menuItems =resortMenuRepository.findAllById(menuIds);

                resort.setMenuItems(menuItems);
            }

            if (newData.containsKey("images")) {
                List<Integer> imageIds = (List<Integer>) newData.get("images");

                List<ResortImage> images = resortImageRepository.findAllById(imageIds);

                resort.getImages().clear();
                resort.getImages().addAll(images);
            }
            resortRepository.save(resort);
            request.setUpdatedAt(LocalDateTime.now());
            request.setRequestStatus(RequestStatus.APPROVED);
        }
        else {
            request.setRequestStatus(RequestStatus.REJECTED);
           
        }
        request.setNote(dto.getNote());
        requestRepository.save(request);
    }

    
}
