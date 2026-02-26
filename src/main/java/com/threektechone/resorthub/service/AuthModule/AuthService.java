package com.threektechone.resorthub.service.AuthModule;
import com.threektechone.resorthub.dto.AuthModuleDTO.AuthRequestDTO;
import com.threektechone.resorthub.dto.AuthModuleDTO.AuthResponseDTO;

public interface AuthService {

    void register(AuthRequestDTO authRequestDTO);

    AuthResponseDTO login(AuthRequestDTO authRequestDTO);
    
}
