package com.threektechone.resorthub.service.pub;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.threektechone.resorthub.dto.pub.PublicResortResponseDetailDTO;
import com.threektechone.resorthub.dto.pub.PublicResortResponseListDTO;
import com.threektechone.resorthub.enums.ResortType;

public interface PublicResortService {
    Page<PublicResortResponseListDTO> getPublicResorts(String searchkey,String city,ResortType type,Pageable pageable);
    PublicResortResponseDetailDTO getResortDetail(int resortId);
}
