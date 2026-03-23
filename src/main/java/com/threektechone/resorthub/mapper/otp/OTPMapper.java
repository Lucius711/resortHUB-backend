package com.threektechone.resorthub.mapper.otp;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.threektechone.resorthub.dto.auth.AuthRequestDTO;
import com.threektechone.resorthub.models.OTP;
import com.threektechone.resorthub.models.User;

@Mapper(componentModel = "spring")
public interface OTPMapper {

    @Mapping(target = "email", source = "email")
    @Mapping(target = "fullName", source = "name")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "gender", source = "gender")
    @Mapping(target = "dob", source = "dob")
    @Mapping(target = "city", source = "city")
    @BeanMapping(ignoreByDefault = true)
    User toUser(OTP otp);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "gender", source = "gender")
    @Mapping(target = "dob", source = "dob")
    @Mapping(target = "city", source = "city")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "verified", constant = "false")
    OTP toOTP(AuthRequestDTO dto);
    
}
