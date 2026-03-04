package com.threektechone.resorthub.service.impl.AdminModuleImpl;

import java.time.LocalDate;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.threektechone.resorthub.ExceptionHandler.CustomException.ResourceNotFoundException;
import com.threektechone.resorthub.dto.AdminModuleDTO.UpdateRoleRequestDTO;
import com.threektechone.resorthub.dto.AdminModuleDTO.UpdateStatusRequestDTO;
import com.threektechone.resorthub.dto.AdminModuleDTO.UserDetailRequestDTO;
import com.threektechone.resorthub.dto.AdminModuleDTO.UserDetailResponseDTO;
import com.threektechone.resorthub.dto.AdminModuleDTO.UserListResponseDTO;
import com.threektechone.resorthub.enums.RoleName;
import com.threektechone.resorthub.enums.UserStatus;
import com.threektechone.resorthub.mapper.UserMapper;
import com.threektechone.resorthub.models.Role;
import com.threektechone.resorthub.models.User;
import com.threektechone.resorthub.repositories.RoleRepository;
import com.threektechone.resorthub.repositories.UserRepository;
import com.threektechone.resorthub.service.AdminModule.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;
    

    //getAllusers with search,gender,roleName and page
    @Override
    public Page<UserListResponseDTO> getAllUsers(String search,Boolean gender,RoleName roleName,UserStatus status,Pageable pageable) {

        Page<User> userList = userRepository.getUserListWithSearchAndFilterAndPagination(search, gender, roleName,status, pageable);
        
        return userList.map(userMapper::toUserListDTO);
    }
    

    //Update information about user
    @Override
    public UserDetailResponseDTO updateUser(int id,UserDetailRequestDTO dto) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        

        user.setFullName(dto.getFullName());
        user.setGender(dto.getGender());
        user.setPhone(dto.getPhone());
        user.setDob(dto.getDob());
        user.setCity(dto.getCity());

        userRepository.save(user);

        return userMapper.toUserDetailResponseDTO(user);
    }
    
    //Update role of a user
    @Override
    public UserDetailResponseDTO updateRole(int id, UpdateRoleRequestDTO dto) {
        User user = userRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Role role = roleRepository.findByRoleName(dto.getRoleName())
        .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        user.setRole(role);

        userRepository.save(user);
        
        return userMapper.toUserDetailResponseDTO(user);
    }
    
    //Update status of user
    @Override
    public UserDetailResponseDTO updateStatus(int id, UpdateStatusRequestDTO dto) {
        User user = userRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setStatus(dto.getStatus());

        userRepository.save(user);

        return userMapper.toUserDetailResponseDTO(user);
    }

    //Delete User with anonymize user details
    @Override
    public void deleteUser(int id) {
        User user = userRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setFullName("Deleted User");
        user.setEmail("deleted_" + user.getUserId() + "@system.local");
        user.setDob(LocalDate.of(1900,1,1));
        user.setPhone("0000000000");
        user.setCity("N/A");
        user.setStatus(UserStatus.INACTIVE);
        user.setIsDeleted(true);
        user.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));

        userRepository.save(user);
    }
}
