package com.threektechone.resorthub.helper.ResortHelper;

import java.util.UUID;

public class ResortCodeGenerator {

    //Generate Resort code
    public static String generateResortCode(){
        return "RS-" + UUID.randomUUID().toString();
    }
    
}
