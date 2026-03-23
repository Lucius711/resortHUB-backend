package com.threektechone.resorthub.service.customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.threektechone.resorthub.dto.customer.BookingCreatedResponseDTO;
import com.threektechone.resorthub.dto.customer.BookingRequestDTO;
import com.threektechone.resorthub.dto.customer.CustomerBookingDetailResponseDTO;
import com.threektechone.resorthub.dto.customer.CustomerBookingListResponseDTO;
import com.threektechone.resorthub.enums.BookingStatus;

public interface CustomerBookingService {
    void createBooking(BookingRequestDTO dto, String email, int resortId);
    Page<CustomerBookingListResponseDTO> getCustomerBookings(String email, String searchkey, BookingStatus status,Pageable pageable);
    CustomerBookingDetailResponseDTO getCustomerBookingDetail(int bookingId);
    void cancelExpiredBookings();
    void cancelBookingByCustomer(int bookingId,String customerMail);
    BookingCreatedResponseDTO payBooking(int bookingId, String customerEmail, String clientIp);
}
