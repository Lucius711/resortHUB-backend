package com.threektechone.resorthub.service.CustomerModule;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.threektechone.resorthub.dto.CustomerModuleDTO.BookingCreatedResponseDTO;
import com.threektechone.resorthub.dto.CustomerModuleDTO.BookingRequestDTO;
import com.threektechone.resorthub.dto.CustomerModuleDTO.CustomerBookingDetailResponseDTO;
import com.threektechone.resorthub.dto.CustomerModuleDTO.CustomerBookingListResponseDTO;
import com.threektechone.resorthub.enums.BookingStatus;

public interface CustomerBookingService {
    void createBooking(BookingRequestDTO dto, String email, int resortId);
    Page<CustomerBookingListResponseDTO> getCustomerBookings(String email, String searchkey, BookingStatus status,Pageable pageable);
    CustomerBookingDetailResponseDTO getCustomerBookingDetail(int bookingId);
    void cancelExpiredBookings();
    void cancelBookingByCustomer(int bookingId,String customerMail);
    BookingCreatedResponseDTO payBooking(int bookingId, String customerEmail, String clientIp);
}
