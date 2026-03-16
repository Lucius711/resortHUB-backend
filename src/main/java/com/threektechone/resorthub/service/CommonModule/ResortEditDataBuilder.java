package com.threektechone.resorthub.service.CommonModule;

import java.util.Map;

import com.threektechone.resorthub.models.Resort;

public interface ResortEditDataBuilder {
    Map<String, Object> buildOldData(Resort resort, Map<String, Object> newData);
}
