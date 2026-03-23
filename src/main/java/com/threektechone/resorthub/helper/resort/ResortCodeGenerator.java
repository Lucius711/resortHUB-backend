package com.threektechone.resorthub.helper.resort;

import java.util.UUID;

public class ResortCodeGenerator {

    //Generate Resort code
    public static String generateResortCode(){
        return "RS-" + UUID.randomUUID().toString();
    }
    
}
