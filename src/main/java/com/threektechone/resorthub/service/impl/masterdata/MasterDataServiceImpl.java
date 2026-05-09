package com.threektechone.resorthub.service.impl.masterdata;

import java.util.List;

import org.springframework.stereotype.Service;

import com.threektechone.resorthub.models.Province;
import com.threektechone.resorthub.models.Ward;
import com.threektechone.resorthub.repositories.ProvinceRepository;
import com.threektechone.resorthub.repositories.WardRepository;
import com.threektechone.resorthub.service.masterdata.MasterDataService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MasterDataServiceImpl implements MasterDataService {

    private final ProvinceRepository provinceRepository;
    private final WardRepository wardRepository;

    @Override
    public List<Province> getProvinces() {
        return provinceRepository.findAll();
    }

    @Override
    public List<Ward> getWardsByProvince(Integer provinceId) {
        return wardRepository.findByProvinceId(provinceId);
    }

}
