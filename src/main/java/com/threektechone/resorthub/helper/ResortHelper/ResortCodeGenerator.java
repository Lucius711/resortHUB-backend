package com.threektechone.resorthub.helper.ResortHelper;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResortCodeGenerator {

    //Generate Resort code
    public String generateResortCode(){
        return "RS" + System.currentTimeMillis();
    }
    
}
