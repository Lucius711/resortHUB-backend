package com.threektechone.resorthub.service.impl.OwnerModuleImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.threektechone.resorthub.dto.OwnerModuleDTO.OwnerResortsResponseDTO;
import com.threektechone.resorthub.enums.ResortStatus;
import com.threektechone.resorthub.mapper.ResortMapper;
import com.threektechone.resorthub.models.Resort;
import com.threektechone.resorthub.repositories.ResortRepository;
import com.threektechone.resorthub.service.OwnerModule.ResortService;

@Service
public class ResortServiceImpl implements ResortService {

    @Autowired
    private ResortRepository resortRepository;

    @Autowired
    private ResortMapper resortMapper;

    @Override
    public Page<OwnerResortsResponseDTO> getAllOwnerResorts(String email,String searchkey, ResortStatus status, Pageable pageable) {
        Page<Resort> resortList = resortRepository.getOwnerResorts(email,searchkey, status, pageable);
        
        return resortList.map(resortMapper::toOwnerResortList);
    }
    
}
