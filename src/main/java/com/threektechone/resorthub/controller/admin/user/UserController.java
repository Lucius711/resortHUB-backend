package com.threektechone.resorthub.controller.admin.user;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.threektechone.resorthub.common.response.ApiResponse;
import com.threektechone.resorthub.dto.admin.UpdateRoleRequestDTO;
import com.threektechone.resorthub.dto.admin.UpdateStatusRequestDTO;
import com.threektechone.resorthub.dto.admin.UserDetailRequestDTO;
import com.threektechone.resorthub.dto.admin.UserDetailResponseDTO;
import com.threektechone.resorthub.dto.admin.UserListResponseDTO;
import com.threektechone.resorthub.enums.RoleName;
import com.threektechone.resorthub.enums.UserStatus;
import com.threektechone.resorthub.service.admin.UserService;

import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
     
    @GetMapping("/users")
    public ResponseEntity<ApiResponse<Page<UserListResponseDTO>>> getUserList(
        @RequestParam(required=false) String search,
        @RequestParam(required=false) Boolean gender, 
        @RequestParam(required=false) RoleName roleName,
        @RequestParam(required=false) UserStatus status,
        @PageableDefault(size=5) Pageable pageable) {

        Page<UserListResponseDTO> userList = userService.getAllUsers(search,gender,roleName,status,pageable);

        ApiResponse<Page<UserListResponseDTO>> response =new ApiResponse<>(200, null, userList, LocalDateTime.now());

        return ResponseEntity.ok(response);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<ApiResponse<UserDetailResponseDTO>> updateUser(@PathVariable int id, @RequestBody UserDetailRequestDTO dto) {
        
        ApiResponse<UserDetailResponseDTO> response =new ApiResponse<>(200, null, userService.updateUser(id, dto), LocalDateTime.now());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/users/{id}/role")
    public ResponseEntity<ApiResponse<UserDetailResponseDTO>> updateRole(@PathVariable int id, @RequestBody UpdateRoleRequestDTO dto) {
        
        ApiResponse<UserDetailResponseDTO> response =new ApiResponse<>(200, null, userService.updateRole(id, dto), LocalDateTime.now());

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/users/{id}/status")
    public ResponseEntity<ApiResponse<UserDetailResponseDTO>> updateStatus(@PathVariable int id, @RequestBody UpdateStatusRequestDTO dto) {
        ApiResponse<UserDetailResponseDTO> response =new ApiResponse<>(200, null, userService.updateStatus(id, dto), LocalDateTime.now());
    
        return ResponseEntity.ok(response);
    }
    

}
