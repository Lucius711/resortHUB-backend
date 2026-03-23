package com.threektechone.resorthub.service.common;

import java.util.Map;

import com.threektechone.resorthub.models.Resort;

public interface ResortEditApplier {
    void applyChanges(Resort resort, Map<String, Object> newData);
    
}
