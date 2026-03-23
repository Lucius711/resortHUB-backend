package com.threektechone.resorthub.service.common;

import java.util.Map;

import com.threektechone.resorthub.models.Resort;

public interface ResortEditDataBuilder {
    Map<String, Object> buildOldData(Resort resort, Map<String, Object> newData);
}
