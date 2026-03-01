package com.threektechone.resorthub.mapper;
import org.springframework.stereotype.Component;

import com.threektechone.resorthub.dto.AdminModuleDTO.UserListResponseDTO;
import com.threektechone.resorthub.dto.AuthModuleDTO.AuthRequestDTO;
import com.threektechone.resorthub.models.User;


@Component
public class UserMapper {

    public User toUser(AuthRequestDTO dto) {
        User user = new User();
        user.setFullName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setGender(dto.isGender());
        user.setDob(dto.getDob());
        user.setCity(dto.getCity());
        return user;
    }

    public UserListResponseDTO toUserListDTO(User user) {
        UserListResponseDTO dto = new UserListResponseDTO();
        dto.setUserId(user.getUserId());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setGender(user.isGender());
        dto.setPhone(user.getPhone());
        dto.setDob(user.getDob());
        dto.setCity(user.getCity());
        dto.setRoleName(user.getRole().getRoleName());
        return dto;
    }
    
}
