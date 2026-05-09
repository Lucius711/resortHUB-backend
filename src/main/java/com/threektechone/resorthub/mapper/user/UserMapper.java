package com.threektechone.resorthub.mapper.user;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.threektechone.resorthub.dto.admin.UserDetailResponseDTO;
import com.threektechone.resorthub.dto.admin.UserListResponseDTO;
import com.threektechone.resorthub.dto.auth.AuthRequestDTO;
import com.threektechone.resorthub.models.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "fullName", source = "name")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "gender", source = "gender")
    @Mapping(target = "dob", source = "dob")
    User toUser(AuthRequestDTO dto);

    @Mapping(target = "roleName", source = "role.roleName")
    @Mapping(target = "provinceId", source = "province.id")
    @Mapping(target = "wardId", source = "ward.id")
    UserDetailResponseDTO toUserDetailResponseDTO(User user);

    @Mapping(target = "roleName", source = "role.roleName")
    @Mapping(target = "provinceId", source = "province.id")
    @Mapping(target = "wardId", source = "ward.id")
    UserListResponseDTO toUserListDTO(User user);

}
