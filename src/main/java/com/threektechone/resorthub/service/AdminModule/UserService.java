package com.threektechone.resorthub.service.AdminModule;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.threektechone.resorthub.dto.AdminModuleDTO.UserListResponseDTO;
import com.threektechone.resorthub.enums.RoleName;
;

public interface UserService {
    
     Page<UserListResponseDTO> getAllUsers(String search, Boolean gender,RoleName roleName,Pageable pageable);
}
