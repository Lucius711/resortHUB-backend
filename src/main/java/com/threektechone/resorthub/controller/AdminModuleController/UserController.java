package com.threektechone.resorthub.controller.AdminModuleController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.threektechone.resorthub.dto.AdminModuleDTO.UpdateRoleRequestDTO;
import com.threektechone.resorthub.dto.AdminModuleDTO.UpdateStatusRequestDTO;
import com.threektechone.resorthub.dto.AdminModuleDTO.UserDetailRequestDTO;
import com.threektechone.resorthub.dto.AdminModuleDTO.UserDetailResponseDTO;
import com.threektechone.resorthub.dto.AdminModuleDTO.UserListResponseDTO;
import com.threektechone.resorthub.enums.RoleName;
import com.threektechone.resorthub.enums.UserStatus;
import com.threektechone.resorthub.service.AdminModule.UserService;



@RestController
@RequestMapping("/api/admin")
public class UserController {

    @Autowired
    private UserService userService;
     
    @GetMapping("/users")
    public Page<UserListResponseDTO> getUserList(@Param("search") String search,@Param("gender") Boolean gender, @Param("roleName") RoleName roleName,@Param("status") UserStatus status,@PageableDefault(size=5) Pageable pageable) {
        Page<UserListResponseDTO> userList = userService.getAllUsers(search,gender,roleName,status,pageable);

        return userList;
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserDetailResponseDTO> updateUser(@PathVariable int id, @RequestBody UserDetailRequestDTO dto) {
    
        return ResponseEntity.ok(userService.updateUser(id, dto));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("Deleted successfully");
    }

    @PatchMapping("/users/{id}/role")
    public ResponseEntity<UserDetailResponseDTO> updateRole(@PathVariable int id, @RequestBody UpdateRoleRequestDTO dto) {
    
        return ResponseEntity.ok(userService.updateRole(id, dto));
    }

    @PatchMapping("/users/{id}/status")
    public ResponseEntity<UserDetailResponseDTO> updateStatus(@PathVariable int id, @RequestBody UpdateStatusRequestDTO dto) {
    
        return ResponseEntity.ok(userService.updateStatus(id, dto));
    }
    

}
