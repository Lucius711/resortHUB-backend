package com.threektechone.resorthub.helper.BookingHelper;

import java.util.UUID;


public class BookingCodeGenerator {
    
    //Generate booking code
    public static String generateBookingCode(){
        return "BK-" + UUID.randomUUID().toString();
    }
    
}
