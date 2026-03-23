package com.threektechone.resorthub.service.impl.staff;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.threektechone.resorthub.common.exception.custom.InvalidEditRequestDataException;
import com.threektechone.resorthub.common.exception.custom.RequestAlreadyReviewedException;
import com.threektechone.resorthub.common.exception.custom.ResourceNotFoundException;
import com.threektechone.resorthub.common.exception.custom.UnauthorizedException;
import com.threektechone.resorthub.dto.staff.EditRequestDecisionDTO;
import com.threektechone.resorthub.dto.staff.EditResponseDetailDTO;
import com.threektechone.resorthub.dto.staff.EditResponseListDTO;
import com.threektechone.resorthub.dto.staff.RegisterRequestDecisionDTO;
import com.threektechone.resorthub.dto.staff.RegisterResponseDetailDTO;
import com.threektechone.resorthub.dto.staff.RegisterResponseListDTO;
import com.threektechone.resorthub.enums.ContractStatus;
import com.threektechone.resorthub.enums.ContractType;
import com.threektechone.resorthub.enums.RequestStatus;
import com.threektechone.resorthub.enums.ResortStatus;
import com.threektechone.resorthub.enums.ReviewAction;
import com.threektechone.resorthub.mapper.editrequest.EditRequestMapper;
import com.threektechone.resorthub.mapper.resort.ResortMapper;
import com.threektechone.resorthub.models.Contract;
import com.threektechone.resorthub.models.EditResortRequest;
import com.threektechone.resorthub.models.Resort;
import com.threektechone.resorthub.models.User;
import com.threektechone.resorthub.repositories.ContractRepository;
import com.threektechone.resorthub.repositories.EditResortRequestRepository;
import com.threektechone.resorthub.repositories.ResortRepository;
import com.threektechone.resorthub.repositories.UserRepository;
import com.threektechone.resorthub.service.common.FileStorageService;
import com.threektechone.resorthub.service.common.ResortEditApplier;
import com.threektechone.resorthub.service.staff.ReviewService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final EditResortRequestRepository requestRepository;
    private final ObjectMapper objectMapper;
    private final ResortRepository resortRepository;
    private final EditRequestMapper requestMapper;
    private final ResortMapper resortMapper;
    private final ResortEditApplier resortEditApplier;
    private final FileStorageService fileStorageService;
    private final ContractRepository contractRepository;
    private final UserRepository userRepository;

    private boolean staffCanManage(String email, Resort resort) {
        return resort.getStaff().getEmail().equals(email);
    }
    

    //Get register resort list
    @Override
    public Page<RegisterResponseListDTO> getAllRegisterResort(String searchkey, ResortStatus status, Pageable pageable) {
        Page<Resort> registerList = resortRepository.getRegisterResorts(searchkey, status, pageable);

        return registerList.map(resortMapper::toRegisterResponseListDTO);
    }
    
    //Get register resort detail
    @Override
    public RegisterResponseDetailDTO getRegisterDetail(int resortId) {
        Resort resort = resortRepository.findById(resortId)
        .orElseThrow(() -> new ResourceNotFoundException("Resort not found!"));

        RegisterResponseDetailDTO dto = resortMapper.toRegisterResponseDetailDTO(resort);
        return dto;
    }
    
    //Review register request from owner
    @Override
    public void reviewRegisterRequest(RegisterRequestDecisionDTO dto,int resortId,String email) {
        Resort resort = resortRepository.findById(resortId)
        .orElseThrow(() -> new ResourceNotFoundException("Resort not found!"));

        User staff = userRepository.findByEmail(email)
        .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        if (resort.getStatus() != ResortStatus.PENDING_REVIEW) {
           throw new RequestAlreadyReviewedException("Request already reviewed");
        }

        if (dto.getAction() == ReviewAction.APPROVE) {
            resort.setStatus(ResortStatus.APPROVED);
        }
        else if (dto.getAction() == ReviewAction.REJECT) {
            resort.setStatus(ResortStatus.REJECTED);
            resort.setReason(dto.getReason());
        }
        resort.setStaff(staff);      
        resortRepository.save(resort);
    }
    
    //send contract to the owner
    @Override
    public void sendContract(int resortId, MultipartFile file, String email,ContractType type) throws IOException {
        String fileUrl = fileStorageService.storeFile(file,"contracts");

        Resort resort = resortRepository.findById(resortId)
                .orElseThrow(() -> new ResourceNotFoundException("Resort not found"));

        User staff = userRepository.findByEmail(email)
        .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        if (!staffCanManage(email, resort)) throw new UnauthorizedException("You dont have permission!");

        if (resort.getStatus() != ResortStatus.APPROVED)
            throw new IllegalStateException("Resort must be APPROVED before sending contract");

        Contract contract = new Contract();
        contract.setResort(resort);
        contract.setStaff(staff);
        contract.setContractType(type);
        contract.setFileUrl(fileUrl);
        contract.setStatus(ContractStatus.PENDING);
        resort.setStatus(ResortStatus.CONTRACT_PENDING);
        contractRepository.save(contract);
    }

    

    //Get pending request list
    @Override
    public Page<EditResponseListDTO> getEditRequests(RequestStatus status,Pageable pageable) {
        Page<EditResortRequest> requestList = requestRepository.getEditRequests(status, pageable);

        return requestList.map(requestMapper::toEditResponseListDTO);
    }
    
    //View edit request detail
    @Override
    public EditResponseDetailDTO getRequestDetail(int requestId) {
        EditResortRequest request = requestRepository.findById(requestId)
        .orElseThrow(() -> new ResourceNotFoundException("Request not found!"));

        EditResponseDetailDTO dto = requestMapper.toEditResponseDetailDTO(request);
        return dto;
    }
    

    //Handle Edit request
    @Override
    public void reviewEditRequest(EditRequestDecisionDTO dto,int requestId,String staffEmail) {
        EditResortRequest request = requestRepository.findById(requestId)
        .orElseThrow(() -> new ResourceNotFoundException("Request not found!"));

        // Only pending requests can be reviewed, and only the assigned staff can decide.
        if (request.getRequestStatus() != RequestStatus.PENDING) {
            throw new RequestAlreadyReviewedException("Request already reviewed");
        }

        if (request.getApprovedBy() == null || request.getApprovedBy().getEmail() == null
                || !request.getApprovedBy().getEmail().equalsIgnoreCase(staffEmail)) {
            throw new UnauthorizedException("You dont have permission!");
        }

        if (ReviewAction.APPROVE.equals(dto.getAction())) {

            Resort resort = request.getResort();

            Map<String, Object> newData;
            try {
                newData = objectMapper.readValue(
                    request.getNewData(),
                    new TypeReference<Map<String, Object>>() {}
                );
            } catch (IOException e) {
                throw new InvalidEditRequestDataException("Failed to parse edit request data",e);
            }
            
            //Handle update
            resortEditApplier.applyChanges(resort, newData);

            
            resortRepository.save(resort);
            request.setUpdatedAt(LocalDateTime.now());
            request.setRequestStatus(RequestStatus.APPROVED);
        }
        else {
            request.setRequestStatus(RequestStatus.REJECTED);
           
        }
        request.setNote(dto.getNote());
        requestRepository.save(request);
    }

  
}
