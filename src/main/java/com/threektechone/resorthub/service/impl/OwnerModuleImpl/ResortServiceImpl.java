package com.threektechone.resorthub.service.impl.OwnerModuleImpl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.threektechone.resorthub.ExceptionHandler.CustomException.InvalidRegisterStepException;
import com.threektechone.resorthub.ExceptionHandler.CustomException.InvalidResortStatusException;
import com.threektechone.resorthub.ExceptionHandler.CustomException.ResourceNotFoundException;
import com.threektechone.resorthub.dto.OwnerModuleDTO.EditRequestDTO;
import com.threektechone.resorthub.dto.OwnerModuleDTO.OwnerResortsResponseDTO;
import com.threektechone.resorthub.dto.OwnerModuleDTO.RegisterAmenitiesRequestDTO;
import com.threektechone.resorthub.dto.OwnerModuleDTO.RegisterBasicInfoRequestDTO;
import com.threektechone.resorthub.dto.OwnerModuleDTO.RegisterCapacityPricingRequestDTO;
import com.threektechone.resorthub.dto.OwnerModuleDTO.RegisterImagesRequestDTO;
import com.threektechone.resorthub.enums.ResortRegistrationStep;
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
    

    //Generate Resort code
    private String generateResortCode(){
        return "RS" + System.currentTimeMillis();
    }
    

    //Get old data of resort
    private Map<String, Object> getOldData(Resort resort, Map<String, Object> newData) {
        Map<String, Object> oldData = new HashMap<>();
        for (String key : newData.keySet()) {
        switch (key) {
            case "name" -> oldData.put("name", resort.getName());

            case "city" -> oldData.put("city", resort.getCity());

            case "district" -> oldData.put("district", resort.getDistrict());

            case "address" -> oldData.put("address", resort.getAddress());

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
    
    //Create resort registraton request 
    @Override
    public int createRegistrationResort(String email) {
        User owner = userRepository.findByEmail(email)
        .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        Resort resort = new Resort();
        resort.setStatus(ResortStatus.DRAFT);
        resort.setResortCode(generateResortCode());
        resort.setStep(ResortRegistrationStep.START);
        resort.setOwner(owner);
        resortRepository.save(resort);
        return resort.getResortId();
    }
    

    //Update resort basic-info
    @Override
    public void updateBasicInfoResort(RegisterBasicInfoRequestDTO dto,int resortId) {
        Resort resort = resortMapper.toResort(dto,resortId);

        if (resort.getStep().ordinal() < ResortRegistrationStep.START.ordinal()) {
            throw new InvalidRegisterStepException("Please read the tutorial first!");
        }

        resort.setStep(ResortRegistrationStep.BASIC_INFO);
        resortRepository.save(resort);
    }
    
    //Update capacity price resort
    @Override
    public void updateCapacityPriceResort(RegisterCapacityPricingRequestDTO dto, int resortId) {
        Resort resort = resortMapper.toResort(dto, resortId);

        if (resort.getStep().ordinal() != ResortRegistrationStep.BASIC_INFO.ordinal()) {
            throw new InvalidRegisterStepException("Please completed basic-info first!");
        }
        resort.setStep(ResortRegistrationStep.CAPACITY_PRICE);
        resortRepository.save(resort);
    }
    
    //Update resort amenities
    @Override
    public void updateAmenitiesResort(RegisterAmenitiesRequestDTO dto, int resortId) {
        Resort resort = resortMapper.toResort(dto, resortId);

        if (resort.getStep().ordinal() != ResortRegistrationStep.CAPACITY_PRICE.ordinal()) {
            throw new InvalidRegisterStepException("Please completed capacity-price first!");
        }
        resort.setStep(ResortRegistrationStep.AMENITIES);
        resortRepository.save(resort);
    }
    

    //Update resort images
    @Override
    public void updateImagesResort(RegisterImagesRequestDTO dto, int resortId) {
        Resort resort = resortMapper.toResort(dto,resortId);

        if (resort.getStep().ordinal() != ResortRegistrationStep.AMENITIES.ordinal()) {
            throw new InvalidRegisterStepException("Please completed amenities first!");
        }
        resort.setStep(ResortRegistrationStep.IMAGES);
        resortRepository.save(resort);
    }
    
    //Submit resort registration
    @Override
    public void submitRegisterResort(int resortId) {
        Resort resort = resortRepository.findById(resortId)
        .orElseThrow(() -> new ResourceNotFoundException("Resort not found!"));

        if(resort.getStatus() != ResortStatus.DRAFT){
            throw new InvalidResortStatusException("Only draft resort can submit");
        }

        if (resort.getStep().ordinal() != ResortRegistrationStep.IMAGES.ordinal()) {
            throw new InvalidRegisterStepException("Please completed images first!");
        }
        resort.setStatus(ResortStatus.PENDING_REVIEW);
        resort.setStep(ResortRegistrationStep.COMPLETED);
        resortRepository.save(resort);
    }

    //Get all owner resorts
    @Override
    public Page<OwnerResortsResponseDTO> getAllOwnerResorts(String email,String searchkey, ResortStatus status, Pageable pageable) {
        Page<Resort> resortList = resortRepository.getOwnerResorts(email,searchkey, status, pageable);
        
        return resortList.map(resortMapper::toOwnerResortList);
    }

    //Create edit resort request
    @Override
    public void createEditRequest(EditRequestDTO dto, String email) {
        Resort resort = resortRepository.findById(dto.getResortId())
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
