package com.threektechone.resorthub.service.impl.OwnerModuleImpl;

import org.springframework.stereotype.Service;

import com.threektechone.resorthub.common.exception.custom.InvalidRegisterStepException;
import com.threektechone.resorthub.common.exception.custom.InvalidResortStatusException;
import com.threektechone.resorthub.enums.ResortRegistrationStep;
import com.threektechone.resorthub.enums.ResortStatus;
import com.threektechone.resorthub.models.Resort;
import com.threektechone.resorthub.models.User;
import com.threektechone.resorthub.service.OwnerModule.ResortRegistrationService;

@Service
public class ResortRegistrationServiceImpl implements ResortRegistrationService {

    @Override
    public void initNewRegistration(Resort resort, User owner, String resortCode) {
        resort.setStatus(ResortStatus.DRAFT);
        resort.setResortCode(resortCode);
        resort.setStep(ResortRegistrationStep.START);
        resort.setOwner(owner);
    }

    @Override
    public void ensureCanUpdateBasicInfo(Resort resort) {
        if (resort.getStep().ordinal() < ResortRegistrationStep.START.ordinal()) {
            throw new InvalidRegisterStepException("Please read the tutorial first!");
        }
    }

    @Override
    public void moveToBasicInfoStep(Resort resort) {
        resort.setStep(ResortRegistrationStep.BASIC_INFO);
    }

    @Override
    public void ensureCanUpdateCapacityPrice(Resort resort) {
         if (resort.getStep().ordinal() != ResortRegistrationStep.BASIC_INFO.ordinal()) {
            throw new InvalidRegisterStepException("Please completed basic-info first!");
        }
    }

    @Override
    public void moveToCapacityPriceStep(Resort resort) {
        resort.setStep(ResortRegistrationStep.CAPACITY_PRICE);
    }

    @Override
    public void ensureCanUpdateAmenities(Resort resort) {
        if (resort.getStep().ordinal() != ResortRegistrationStep.CAPACITY_PRICE.ordinal()) {
            throw new InvalidRegisterStepException("Please completed capacity-price first!");
        }
    }

    @Override
    public void moveToAmenitiesStep(Resort resort) {
        resort.setStep(ResortRegistrationStep.AMENITIES);
    }

    @Override
    public void ensureCanUpdateImages(Resort resort) {
        if (resort.getStep().ordinal() != ResortRegistrationStep.AMENITIES.ordinal()) {
            throw new InvalidRegisterStepException("Please completed amenities first!");
        }
    }

    @Override
    public void moveToImagesStep(Resort resort) {
        resort.setStep(ResortRegistrationStep.IMAGES);
    }

     @Override
    public void ensureCanUpdateMenus(Resort resort) {
        if (resort.getStep().ordinal() != ResortRegistrationStep.IMAGES.ordinal()) {
            throw new InvalidRegisterStepException("Please completed images first!");
        }
    }

    @Override
    public void moveToMenusStep(Resort resort) {
        resort.setStep(ResortRegistrationStep.MENUS);
    }


    @Override
    public void ensureCanSubmit(Resort resort) {
        if(resort.getStatus() != ResortStatus.DRAFT){
            throw new InvalidResortStatusException("Only draft resort can submit");
        }

        if (resort.getStep().ordinal() != ResortRegistrationStep.MENUS.ordinal()) {
            throw new InvalidRegisterStepException("Please completed menus first!");
        }
    }

    @Override
    public void submit(Resort resort) {
        resort.setStatus(ResortStatus.PENDING_REVIEW);
        resort.setStep(ResortRegistrationStep.COMPLETED);
    }

    
}
