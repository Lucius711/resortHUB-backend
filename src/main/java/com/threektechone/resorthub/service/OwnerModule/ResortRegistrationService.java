package com.threektechone.resorthub.service.OwnerModule;

import com.threektechone.resorthub.models.Resort;
import com.threektechone.resorthub.models.User;

public interface ResortRegistrationService {
    void initNewRegistration(Resort resort,User owner,String resortCode);
    void ensureCanUpdateBasicInfo(Resort resort);
    void moveToBasicInfoStep(Resort resort);
    void ensureCanUpdateCapacityPrice(Resort resort);
    void moveToCapacityPriceStep(Resort resort);
    void ensureCanUpdateAmenities(Resort resort);
    void moveToAmenitiesStep(Resort resort);
    void ensureCanUpdateImages(Resort resort);
    void moveToImagesStep(Resort resort);
    void ensureCanSubmit(Resort resort);
    void submit(Resort resort);
}
