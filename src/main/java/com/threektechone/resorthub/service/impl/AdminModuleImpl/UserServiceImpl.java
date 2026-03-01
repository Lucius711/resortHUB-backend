package com.threektechone.resorthub.service.impl.AdminModuleImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.threektechone.resorthub.dto.AdminModuleDTO.UserListResponseDTO;
import com.threektechone.resorthub.enums.RoleName;
import com.threektechone.resorthub.mapper.UserMapper;
import com.threektechone.resorthub.models.User;
import com.threektechone.resorthub.repositories.UserRepository;
import com.threektechone.resorthub.service.AdminModule.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;
    

    //getAllusers with search,gender,roleName and page
    @Override
    public Page<UserListResponseDTO> getAllUsers(String search,Boolean gender,RoleName roleName,Pageable pageable) {

        Page<User> userList = userRepository.getUserListWithSearchAndFilterAndPagination(search, gender, roleName, pageable);
        
        return userList.map(userMapper::toUserListDTO);
    }
    
}
