package com.threektechone.resorthub.service.impl.owner;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.threektechone.resorthub.common.exception.custom.ActiveBookingExistsException;
import com.threektechone.resorthub.common.exception.custom.ActiveUnfinishedPaymentException;
import com.threektechone.resorthub.common.exception.custom.InvalidContractStatusException;
import com.threektechone.resorthub.common.exception.custom.InvalidEditRequestDataException;
import com.threektechone.resorthub.common.exception.custom.InvalidResortStatusException;
import com.threektechone.resorthub.common.exception.custom.ResourceNotFoundException;
import com.threektechone.resorthub.common.exception.custom.UnauthorizedException;
import com.threektechone.resorthub.dto.owner.EditRequestDTO;
import com.threektechone.resorthub.dto.owner.OwnerResortsDetailResponseDTO;
import com.threektechone.resorthub.dto.owner.OwnerResortsListResponseDTO;
import com.threektechone.resorthub.dto.owner.RegisterAmenitiesRequestDTO;
import com.threektechone.resorthub.dto.owner.RegisterBasicInfoRequestDTO;
import com.threektechone.resorthub.dto.owner.RegisterCapacityPricingRequestDTO;
import com.threektechone.resorthub.dto.owner.RegisterImagesRequestDTO;
import com.threektechone.resorthub.dto.owner.RegisterMenusRequestDTO;
import com.threektechone.resorthub.enums.BookingStatus;
import com.threektechone.resorthub.enums.ContractStatus;
import com.threektechone.resorthub.enums.PaymentStatus;
import com.threektechone.resorthub.enums.ResortStatus;
import com.threektechone.resorthub.helper.resort.ResortCodeGenerator;
import com.threektechone.resorthub.mapper.editrequest.EditRequestMapper;
import com.threektechone.resorthub.mapper.resort.ResortMapper;
import com.threektechone.resorthub.mapper.resort.ResortMenuMapper;
import com.threektechone.resorthub.models.Contract;
import com.threektechone.resorthub.models.EditResortRequest;
import com.threektechone.resorthub.models.Resort;
import com.threektechone.resorthub.models.ResortImage;
import com.threektechone.resorthub.models.ResortMenu;
import com.threektechone.resorthub.models.User;
import com.threektechone.resorthub.repositories.BookingRepository;
import com.threektechone.resorthub.repositories.ContractRepository;
import com.threektechone.resorthub.repositories.EditResortRequestRepository;
import com.threektechone.resorthub.repositories.ResortRepository;
import com.threektechone.resorthub.repositories.UserRepository;
import com.threektechone.resorthub.service.common.ResortEditDataBuilder;
import com.threektechone.resorthub.service.owner.ResortRegistrationService;
import com.threektechone.resorthub.service.owner.ResortService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResortServiceImpl implements ResortService {

    private final ResortRepository resortRepository;

    private final ResortMapper resortMapper;

    private final UserRepository userRepository;

    private final ObjectMapper objectMapper;

    private final EditResortRequestRepository editResortRequestRepository;

    private final EditRequestMapper editRequestMapper;

    private final ResortEditDataBuilder resortEditDataBuilder;

    private final ResortRegistrationService resortRegistrationService;

    private final ResortMenuMapper resortMenuMapper;

    private final ContractRepository contractRepository;

    private final BookingRepository bookingRepository;

    private void ensureOwnerAccess(Resort resort, String ownerEmail) {
        if (resort == null || resort.getOwner() == null || resort.getOwner().getEmail() == null
                || !resort.getOwner().getEmail().equalsIgnoreCase(ownerEmail)) {
            throw new UnauthorizedException("You dont have permission!");
        }
    }

    // Create resort registraton request
    @Override
    public int createRegistrationResort(String email) {
        User owner = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        Resort resort = new Resort();
        resortRegistrationService.initNewRegistration(resort, owner, ResortCodeGenerator.generateResortCode());
        resortRepository.save(resort);
        return resort.getResortId();
    }

    // Update resort basic-info
    @Override
    public void updateBasicInfoResort(RegisterBasicInfoRequestDTO dto, int resortId, String ownerEmail) {
        Resort resort = resortRepository.findById(resortId)
                .orElseThrow(() -> new ResourceNotFoundException("Resort not found!"));

        ensureOwnerAccess(resort, ownerEmail);

        resortRegistrationService.ensureCanUpdateBasicInfo(resort);
        resortMapper.updateResortBasicInfo(resort, dto);
        resortRegistrationService.moveToBasicInfoStep(resort);

        resortRepository.save(resort);
    }

    // Update capacity price resort
    @Override
    public void updateCapacityPriceResort(RegisterCapacityPricingRequestDTO dto, int resortId, String ownerEmail) {
        Resort resort = resortRepository.findById(resortId)
                .orElseThrow(() -> new ResourceNotFoundException("Resort not found!"));

        ensureOwnerAccess(resort, ownerEmail);

        resortRegistrationService.ensureCanUpdateCapacityPrice(resort);
        resortMapper.updateResortCapacityPrice(resort, dto);
        resortRegistrationService.moveToCapacityPriceStep(resort);
        resortRepository.save(resort);
    }

    // Update resort amenities
    @Override
    public void updateAmenitiesResort(RegisterAmenitiesRequestDTO dto, int resortId, String ownerEmail) {

        Resort resort = resortRepository.findById(resortId)
                .orElseThrow(() -> new ResourceNotFoundException("Resort not found!"));

        ensureOwnerAccess(resort, ownerEmail);

        resortRegistrationService.ensureCanUpdateAmenities(resort);
        resortMapper.updateResortAmenities(resort, dto);
        resortRegistrationService.moveToAmenitiesStep(resort);
        resortRepository.save(resort);
    }

    // Update resort images
    @Override
    public void updateImagesResort(RegisterImagesRequestDTO dto, int resortId, String ownerEmail) {

        Resort resort = resortRepository.findById(resortId)
                .orElseThrow(() -> new ResourceNotFoundException("Resort not found!"));

        ensureOwnerAccess(resort, ownerEmail);

        List<ResortImage> images = resortMapper.mapImageIds(dto.getImageUrls());

        resortRegistrationService.ensureCanUpdateImages(resort);
        images.forEach(img -> img.setResort(resort));
        resort.getImages().clear();
        resort.getImages().addAll(images);
        resortRegistrationService.moveToImagesStep(resort);
        resortRepository.save(resort);
    }

    // Update resort menu
    @Override
    public void updateMenusResort(RegisterMenusRequestDTO dto, int resortId, String ownerEmail) {
        Resort resort = resortRepository.findById(resortId)
                .orElseThrow(() -> new ResourceNotFoundException("Resort not found!"));

        ensureOwnerAccess(resort, ownerEmail);

        List<ResortMenu> menus = resortMenuMapper.toResortMenuList(dto.getMenus());

        resortRegistrationService.ensureCanUpdateMenus(resort);
        for (ResortMenu menu : menus) {
            menu.setResort(resort);
        }
        resort.getMenuItems().clear();
        resort.getMenuItems().addAll(menus);
        resortRegistrationService.moveToMenusStep(resort);
        resortRepository.save(resort);
    }

    // Submit resort registration
    @Override
    public void submitRegisterResort(int resortId, String ownerEmail) {
        Resort resort = resortRepository.findById(resortId)
                .orElseThrow(() -> new ResourceNotFoundException("Resort not found!"));

        ensureOwnerAccess(resort, ownerEmail);

        resortRegistrationService.ensureCanSubmit(resort);
        resortRegistrationService.submit(resort);
        resortRepository.save(resort);
    }

    // sign contract
    @Override
    public void signContract(int resortId, MultipartFile file, Boolean acceptedTerms, String ownerEmail) {
        Resort resort = resortRepository.findById(resortId)
                .orElseThrow(() -> new ResourceNotFoundException("Resort not found!"));

        ensureOwnerAccess(resort, ownerEmail);

        if (acceptedTerms == null || !acceptedTerms) {
            throw new InvalidContractStatusException("You must accept the terms to sign the contract");
        }

        Contract contract = resort.getContracts().stream()
                .filter(c -> c.getStatus() == ContractStatus.PENDING)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("No contract pending signature for this resort"));

        resortRegistrationService.ensureCanSignContract(resort, contract);

        contract.setSignedByOwner(true);
        contract.setAcceptedTerms(acceptedTerms);
        contract.setSignedAt(LocalDateTime.now());
        contract.setStatus(ContractStatus.ACTIVE);
        contract.setOwner(resort.getOwner());
        resortRegistrationService.signContract(contract, resort);
        contractRepository.save(contract);
    }

    // Get all owner resorts
    @Override
    public Page<OwnerResortsListResponseDTO> getAllOwnerResorts(String email, String searchkey, ResortStatus status,
            Pageable pageable) {
        Page<Resort> resortList = resortRepository.getOwnerResorts(email, searchkey, status, pageable);

        return resortList.map(resortMapper::toOwnerResortList);
    }
    
    //Get owner resorts detail
    @Override
    public OwnerResortsDetailResponseDTO getOwnerResortsDetail(int resortId, String ownerEmail) {
        Resort resort = resortRepository.findById(resortId)
        .orElseThrow(() -> new ResourceNotFoundException("Resort not found!"));

        OwnerResortsDetailResponseDTO dto = resortMapper.toOwnerResortsDetail(resort);
        return dto;
    }
    
    //Inactive resort
    @Override
    public void inactiveResort(int resortId, String ownerEmail) {
        Resort resort = resortRepository.findById(resortId)
        .orElseThrow(() -> new ResourceNotFoundException("Resort not found!"));

        if (!resort.getOwner().getEmail().equals(ownerEmail)) {
            throw new UnauthorizedException("You are not the owner of this resort!");
        }
        boolean hasActiveBooking = bookingRepository.existsByResortIdAndStatusIn(
            resortId,
            List.of(
                BookingStatus.PENDING,
                BookingStatus.APPROVED,
                BookingStatus.CHECKED_IN
            )
        );
        if (hasActiveBooking) {
            throw new ActiveBookingExistsException("Resort cannot be deactivated while there are active bookings.");
        }

        boolean hasUnfinishedPayment  = bookingRepository.existsByResortIdAndPaymentStatusIn(
            resortId,
            List.of(
                PaymentStatus.PENDING
            )
        );

        if (hasUnfinishedPayment) {
            throw new ActiveUnfinishedPaymentException("Resort cannot be deactivated while there are unfinished payment.");
        }

        resort.setStatus(ResortStatus.INACTIVE);
        resortRepository.save(resort);
    }

    // Create edit resort request
    @Override
    public void createEditRequest(EditRequestDTO dto, String email) {
        Resort resort = resortRepository.findById(dto.getResortId())
                .orElseThrow(() -> new ResourceNotFoundException("Resort not found!"));

        ensureOwnerAccess(resort, email);

        if (resort.getStatus() != ResortStatus.ACTIVE) {
            throw new InvalidResortStatusException("Only ACTIVE resorts can be edited");
        }

        if (resort.getStaff() == null) {
            throw new InvalidResortStatusException("Staff must be assigned to edit this resort");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));

        Map<String, Object> oldData = resortEditDataBuilder.buildOldData(resort, dto.getNewData());

        EditResortRequest request = editRequestMapper.toRequest(dto);
        request.setResort(resort);

        try {
            String oldDataJson = objectMapper.writeValueAsString(oldData);
            String newDataJson = objectMapper.writeValueAsString(dto.getNewData());
            request.setOldData(oldDataJson);
            request.setNewData(newDataJson);
        } catch (JsonProcessingException e) {
            throw new InvalidEditRequestDataException("Failed to serialize edit request data", e);
        }

        request.setCreatedBy(user);
        request.setApprovedBy(resort.getStaff());

        editResortRequestRepository.save(request);
    }

}
