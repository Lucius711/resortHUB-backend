package com.threektechone.resorthub.service.masterdata;

import java.util.List;

import com.threektechone.resorthub.models.Province;
import com.threektechone.resorthub.models.Ward;

public interface MasterDataService {
    List<Province> getProvinces();

    List<Ward> getWardsByProvince(Integer provinceId);
}
