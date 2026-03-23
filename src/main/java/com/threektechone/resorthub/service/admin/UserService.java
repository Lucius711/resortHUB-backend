package com.threektechone.resorthub.service.admin;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.threektechone.resorthub.dto.admin.UpdateRoleRequestDTO;
import com.threektechone.resorthub.dto.admin.UpdateStatusRequestDTO;
import com.threektechone.resorthub.dto.admin.UserDetailRequestDTO;
import com.threektechone.resorthub.dto.admin.UserDetailResponseDTO;
import com.threektechone.resorthub.dto.admin.UserListResponseDTO;
import com.threektechone.resorthub.enums.RoleName;
import com.threektechone.resorthub.enums.UserStatus;
;

public interface UserService {
    
     Page<UserListResponseDTO> getAllUsers(String search, Boolean gender,RoleName roleName,UserStatus status,Pageable pageable);

     UserDetailResponseDTO updateUser(int id,UserDetailRequestDTO dto);

     UserDetailResponseDTO updateRole(int id,UpdateRoleRequestDTO dto);

     UserDetailResponseDTO updateStatus(int id,UpdateStatusRequestDTO dto);

     void deleteUser(int id);

}
