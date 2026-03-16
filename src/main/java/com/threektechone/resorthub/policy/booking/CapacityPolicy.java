package com.threektechone.resorthub.policy.booking;

import org.springframework.stereotype.Component;

import com.threektechone.resorthub.common.exception.custom.CapacityExceededException;

@Component
public class CapacityPolicy {

    public void validateCapacity(int numberOfPerson, int maxGuest) {
        if (numberOfPerson > maxGuest) {
            throw new CapacityExceededException("Number of guests exceeds room capacity");
        }
    }

}
