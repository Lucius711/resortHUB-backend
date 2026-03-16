package com.threektechone.resorthub.service.CommonModule;

import java.util.Map;

import com.threektechone.resorthub.models.Resort;

public interface ResortEditApplier {
    void applyChanges(Resort resort, Map<String, Object> newData);
    
}
