package com.threektechone.resorthub.helper.BookingHelper;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingCodeGenerator {

    //Generate booking code
    public String generateBookingCode(){
        return "BK" + System.currentTimeMillis();
    }
    
}
