package com.threektechone.resorthub.mapper.otp;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.threektechone.resorthub.dto.auth.RegisterCacheDTO;
import com.threektechone.resorthub.models.User;

@Mapper(componentModel = "spring")
public interface RegisterCacheMapper {

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "userCode", ignore = true)
    @Mapping(target = "image", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "isDeleted", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "createdAt", ignore = true)

    @Mapping(target = "resorts", ignore = true)
    @Mapping(target = "bookings", ignore = true)
    @Mapping(target = "lostFoundItems", ignore = true)
    @Mapping(target = "ASideContracts", ignore = true)
    @Mapping(target = "BSideContracts", ignore = true)
    @Mapping(target = "sentMessages", ignore = true)
    @Mapping(target = "receivedMessages", ignore = true)
    @Mapping(target = "refreshTokens", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "requests", ignore = true)
    @Mapping(target = "province", ignore = true)
    @Mapping(target = "ward", ignore = true)

    @Mapping(target = "fullName", source = "name")
    User toUser(RegisterCacheDTO cache);

}
