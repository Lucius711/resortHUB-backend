package com.threektechone.resorthub.service.impl.OwnerModuleImpl;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.threektechone.resorthub.common.exception.custom.InvalidEditRequestDataException;
import com.threektechone.resorthub.common.exception.custom.ResourceNotFoundException;
import com.threektechone.resorthub.dto.OwnerModuleDTO.EditRequestDTO;
import com.threektechone.resorthub.dto.OwnerModuleDTO.OwnerResortsResponseDTO;
import com.threektechone.resorthub.dto.OwnerModuleDTO.RegisterAmenitiesRequestDTO;
import com.threektechone.resorthub.dto.OwnerModuleDTO.RegisterBasicInfoRequestDTO;
import com.threektechone.resorthub.dto.OwnerModuleDTO.RegisterCapacityPricingRequestDTO;
import com.threektechone.resorthub.dto.OwnerModuleDTO.RegisterImagesRequestDTO;
import com.threektechone.resorthub.dto.OwnerModuleDTO.RegisterMenusRequestDTO;
import com.threektechone.resorthub.enums.ResortStatus;
import com.threektechone.resorthub.helper.ResortHelper.ResortCodeGenerator;
import com.threektechone.resorthub.helper.ResortHelper.ResortEditDataBuilder;
import com.threektechone.resorthub.mapper.EditRequestMapper;
import com.threektechone.resorthub.mapper.ResortMapper;
import com.threektechone.resorthub.mapper.ResortMenuMapper;
import com.threektechone.resorthub.models.EditResortRequest;
import com.threektechone.resorthub.models.Resort;
import com.threektechone.resorthub.models.ResortImage;
import com.threektechone.resorthub.models.ResortMenu;
import com.threektechone.resorthub.models.User;
import com.threektechone.resorthub.repositories.EditResortRequestRepository;
import com.threektechone.resorthub.repositories.ResortRepository;
import com.threektechone.resorthub.repositories.UserRepository;
import com.threektechone.resorthub.service.OwnerModule.ResortRegistrationService;
import com.threektechone.resorthub.service.OwnerModule.ResortService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResortServiceImpl implements ResortService {

    private final ResortRepository resortRepository;

    private final ResortMapper resortMapper;

    private final UserRepository userRepository;

    private final ObjectMapper objectMapper;

    private final EditResortRequestRepository editResortRequestRepository;

    private final EditRequestMapper editRequestMapper;

    private final ResortEditDataBuilder resortEditDataBuilder;

    private final ResortRegistrationService resortRegistrationService;

    private final ResortCodeGenerator resortCodeGenerator;

    private final ResortMenuMapper resortMenuMapper;
    
    
    //Create resort registraton request 
    @Override
    public int createRegistrationResort(String email) {
        User owner = userRepository.findByEmail(email)
        .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        Resort resort = new Resort();
        resortRegistrationService.initNewRegistration(resort, owner, resortCodeGenerator.generateResortCode());
        resortRepository.save(resort);
        return resort.getResortId();
    }
    

    //Update resort basic-info
    @Override
    public void updateBasicInfoResort(RegisterBasicInfoRequestDTO dto,int resortId) {
        Resort resort = resortRepository.findById(resortId)
        .orElseThrow(() -> new ResourceNotFoundException("Resort not found!"));
        
        resortRegistrationService.ensureCanUpdateBasicInfo(resort);
        resortMapper.updateResortBasicInfo(resort, dto);
        resortRegistrationService.moveToBasicInfoStep(resort);

        resortRepository.save(resort);
    }
    
    //Update capacity price resort
    @Override
    public void updateCapacityPriceResort(RegisterCapacityPricingRequestDTO dto, int resortId) {
        Resort resort = resortRepository.findById(resortId)
        .orElseThrow(() -> new ResourceNotFoundException("Resort not found!"));
        
        resortRegistrationService.ensureCanUpdateCapacityPrice(resort);
        resortMapper.updateResortCapacityPrice(resort, dto);
        resortRegistrationService.moveToCapacityPriceStep(resort);
        resortRepository.save(resort);
    }
    
    //Update resort amenities
    @Override
    public void updateAmenitiesResort(RegisterAmenitiesRequestDTO dto, int resortId) {
        
        Resort resort = resortRepository.findById(resortId)
        .orElseThrow(() -> new ResourceNotFoundException("Resort not found!"));

        resortRegistrationService.ensureCanUpdateAmenities(resort);
        resortMapper.updateResortAmenities(resort, dto);
        resortRegistrationService.moveToAmenitiesStep(resort);
        resortRepository.save(resort);
    }
    

    //Update resort images
    @Override
    public void updateImagesResort(RegisterImagesRequestDTO dto, int resortId) {

        Resort resort = resortRepository.findById(resortId)
        .orElseThrow(() -> new ResourceNotFoundException("Resort not found!"));

        List<ResortImage> images = resortMapper.mapImageIds(dto.getImageUrls());
        
        resortRegistrationService.ensureCanUpdateImages(resort);
        images.forEach(img -> img.setResort(resort));
        resort.getImages().clear();    
        resort.getImages().addAll(images);
        resortRegistrationService.moveToImagesStep(resort);
        resortRepository.save(resort);
    }
    
    //Update resort menu
    @Override
    public void updateMenusResort(RegisterMenusRequestDTO dto, int resortId) {
        Resort resort = resortRepository.findById(resortId)
        .orElseThrow(() -> new ResourceNotFoundException("Resort not found!"));

        List<ResortMenu> menus = resortMenuMapper.toResortMenuList(dto.getMenus());

        resortRegistrationService.ensureCanUpdateMenus(resort);
        for (ResortMenu menu : menus) {
            menu.setResort(resort);
        }
        resort.getMenuItems().clear();   
        resort.getMenuItems().addAll(menus);
        resortRegistrationService.moveToMenusStep(resort);
        resortRepository.save(resort);
    }
    
    //Submit resort registration
    @Override
    public void submitRegisterResort(int resortId) {
        Resort resort = resortRepository.findById(resortId)
        .orElseThrow(() -> new ResourceNotFoundException("Resort not found!"));

        resortRegistrationService.ensureCanSubmit(resort);
        resortRegistrationService.submit(resort);
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

        Map<String, Object> oldData = resortEditDataBuilder.buildOldData(resort, dto.getNewData());

        EditResortRequest request = editRequestMapper.toRequest(dto);
        request.setResort(resort);

        try {
            String oldDataJson = objectMapper.writeValueAsString(oldData);
            String newDataJson = objectMapper.writeValueAsString(dto.getNewData());
            request.setOldData(oldDataJson);
            request.setNewData(newDataJson);
        } catch (JsonProcessingException e) {
            throw new InvalidEditRequestDataException("Failed to serialize edit request data", e);
        }

        request.setCreatedBy(user);
        request.setApprovedBy(resort.getStaff());

        editResortRequestRepository.save(request);
    }
  
}
