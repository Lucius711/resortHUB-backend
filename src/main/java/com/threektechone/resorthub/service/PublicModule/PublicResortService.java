package com.threektechone.resorthub.service.PublicModule;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.threektechone.resorthub.dto.PublicModuleDTO.PublicResortResponseListDTO;
import com.threektechone.resorthub.enums.ResortType;

public interface PublicResortService {
    Page<PublicResortResponseListDTO> getPublicResorts(String searchkey,String city,ResortType type,Pageable pageable);
}
