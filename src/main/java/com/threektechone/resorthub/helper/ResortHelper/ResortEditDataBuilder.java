package com.threektechone.resorthub.helper.ResortHelper;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.threektechone.resorthub.enums.EditResortField;
import com.threektechone.resorthub.models.Resort;
import com.threektechone.resorthub.models.ResortAmenity;
import com.threektechone.resorthub.models.ResortImage;
import com.threektechone.resorthub.models.ResortMenu;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResortEditDataBuilder {

    //Get old data of resort
    public Map<String, Object> buildOldData(Resort resort, Map<String, Object> newData) {
        Map<String, Object> oldData = new HashMap<>();    
        for (String key : newData.keySet()) {
        EditResortField field = EditResortField.fromKey(key);
        switch (field) {
            case NAME -> oldData.put(field.key(), resort.getName());

            case CITY -> oldData.put(field.key(), resort.getCity());

            case DISTRICT -> oldData.put(field.key(), resort.getDistrict());

            case ADDRESS -> oldData.put(field.key(), resort.getAddress());

            case DESCRIPTION -> oldData.put(field.key(), resort.getDescription());
    
            case TYPE -> oldData.put(field.key(), resort.getType());

            case MAX_GUEST -> oldData.put(field.key(),resort.getMaxGuest());

            case AVERAGE_RATING -> oldData.put(field.key(), resort.getAverageRating());

            case AMENITIES -> oldData.put(field.key(),resort.getAmenities().stream()
            .map(ResortAmenity::getAmenityId).toList());

            case MENU_ITEMS -> oldData.put(field.key(),resort.getMenuItems().stream()
            .map(ResortMenu::getMenuId).toList());

            case IMAGES -> oldData.put(field.key(),resort.getImages().stream()
            .map(ResortImage::getImageId).toList());

            case PRICE -> oldData.put(field.key(),resort.getPrice());
    
        }
    }
    return oldData;
}
}
