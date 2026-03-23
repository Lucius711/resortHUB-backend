package com.threektechone.resorthub.service.impl.common;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.threektechone.resorthub.enums.EditResortField;
import com.threektechone.resorthub.enums.ResortType;
import com.threektechone.resorthub.models.Resort;
import com.threektechone.resorthub.repositories.ResortAmenityRepository;
import com.threektechone.resorthub.repositories.ResortImageRepository;
import com.threektechone.resorthub.repositories.ResortMenuRepository;
import com.threektechone.resorthub.service.common.ResortEditApplier;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResortEditApplierImpl implements ResortEditApplier {

    private final ResortAmenityRepository resortAmenityRepository;
    private final ResortMenuRepository resortMenuRepository;
    private final ResortImageRepository resortImageRepository;
    

    //apply new data for resort
    @Override
    public void applyChanges(Resort resort, Map<String, Object> newData) {
        for (String key : newData.keySet()) {
            EditResortField field = EditResortField.fromKey(key);
            Object value = newData.get(key);
            switch (field) {
                case NAME -> resort.setName((String) value);
                case CITY -> resort.setCity((String) value);
                case DISTRICT -> resort.setDistrict((String) value);
                case ADDRESS -> resort.setAddress((String) value);
                case DESCRIPTION -> resort.setDescription((String) value);
                case TYPE -> resort.setType((ResortType) value);
                case MAX_GUEST ->
                    resort.setMaxGuest(Integer.parseInt(value.toString()));
                case AVERAGE_RATING ->
                    resort.setAverageRating(new BigDecimal(value.toString()));
                case PRICE ->
                    resort.setPrice(new BigDecimal(value.toString()));
                case AMENITIES -> {
                    List<Integer> amenityIds = (List<Integer>) value;
                    var amenities = resortAmenityRepository.findAllById(amenityIds);
                    resort.setAmenities(new HashSet<>(amenities));
                }
                case MENU_ITEMS -> {
                    List<Integer> menuIds = (List<Integer>) value;
                    var menuItems = resortMenuRepository.findAllById(menuIds);
                    resort.setMenuItems(menuItems);
                }
                case IMAGES -> {
                    List<Integer> imageIds = (List<Integer>) value;
                    var images = resortImageRepository.findAllById(imageIds);
                    resort.getImages().clear();
                    resort.getImages().addAll(images);
                }
            }
        }
    }
    
}
