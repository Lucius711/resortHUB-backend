package com.threektechone.resorthub.service.impl.PublicModuleImpl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.threektechone.resorthub.common.exception.custom.ResourceNotFoundException;
import com.threektechone.resorthub.dto.PublicModuleDTO.PublicResortResponseDetailDTO;
import com.threektechone.resorthub.dto.PublicModuleDTO.PublicResortResponseListDTO;
import com.threektechone.resorthub.enums.ResortType;
import com.threektechone.resorthub.mapper.ResortMapper;
import com.threektechone.resorthub.models.Resort;
import com.threektechone.resorthub.repositories.ResortRepository;
import com.threektechone.resorthub.service.PublicModule.PublicResortService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PublicResortServiceImpl implements PublicResortService {
    
    private final ResortRepository resortRepository;
    private final ResortMapper resortMapper;

    @Override
    public Page<PublicResortResponseListDTO> getPublicResorts(String searchkey, String city, ResortType type, Pageable pageable) {
        Page<Resort> resortList = resortRepository.getPublicResorts(searchkey, city, type, pageable);

        return resortList.map(resortMapper::toPublicResortResponseListDTO);
    }

    @Override
    public PublicResortResponseDetailDTO getResortDetail(int resortId) {
        Resort resort = resortRepository.findById(resortId)
        .orElseThrow(() -> new ResourceNotFoundException("Resort not found!"));

        PublicResortResponseDetailDTO dto = resortMapper.toPublicResortResponseDetailDTO(resort);

        return dto;
    }
    
}
