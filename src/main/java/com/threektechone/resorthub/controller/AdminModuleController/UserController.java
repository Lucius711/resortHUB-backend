package com.threektechone.resorthub.controller.AdminModuleController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.threektechone.resorthub.dto.AdminModuleDTO.UserListResponseDTO;
import com.threektechone.resorthub.enums.RoleName;
import com.threektechone.resorthub.service.AdminModule.UserService;


@RestController
@RequestMapping("/api/admin")
public class UserController {

    @Autowired
    private UserService userService;
     
    @GetMapping("/users")
    public Page<UserListResponseDTO> getUserList(@Param("search") String search,@Param("gender") Boolean gender, @Param("roleName") RoleName roleName,@PageableDefault(size=5) Pageable pageable) {
        Page<UserListResponseDTO> userList = userService.getAllUsers(search,gender,roleName,pageable);

        return userList;
    }
    

}
