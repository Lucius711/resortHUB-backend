package com.threektechone.resorthub.service.owner;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.threektechone.resorthub.dto.owner.EditRequestDTO;
import com.threektechone.resorthub.dto.owner.OwnerResortsResponseDTO;
import com.threektechone.resorthub.dto.owner.RegisterAmenitiesRequestDTO;
import com.threektechone.resorthub.dto.owner.RegisterBasicInfoRequestDTO;
import com.threektechone.resorthub.dto.owner.RegisterCapacityPricingRequestDTO;
import com.threektechone.resorthub.dto.owner.RegisterImagesRequestDTO;
import com.threektechone.resorthub.dto.owner.RegisterMenusRequestDTO;
import com.threektechone.resorthub.enums.ResortStatus;

public interface ResortService {
    Page<OwnerResortsResponseDTO> getAllOwnerResorts(String email,String searchkey,ResortStatus status,Pageable pageable);
    void createEditRequest(EditRequestDTO dto,String email);
    int createRegistrationResort(String email);
    void updateBasicInfoResort(RegisterBasicInfoRequestDTO dto,int resortId,String ownerEmail);
    void updateCapacityPriceResort(RegisterCapacityPricingRequestDTO dto,int resortId,String ownerEmail);
    void updateAmenitiesResort(RegisterAmenitiesRequestDTO dto,int resortId,String ownerEmail);
    void updateImagesResort(RegisterImagesRequestDTO dto,int resortId,String ownerEmail);
    void updateMenusResort(RegisterMenusRequestDTO dto,int resortId,String ownerEmail);
    void submitRegisterResort(int resortId,String ownerEmail);
    void signContract(int resortId,MultipartFile file,Boolean acceptedTerms,String ownerEmail);   
}
