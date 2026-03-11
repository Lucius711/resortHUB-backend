package com.threektechone.resorthub.service.CustomerModule;

import com.threektechone.resorthub.dto.CustomerModuleDTO.BookingRequestDTO;

public interface BookingService {
    void createBooking(BookingRequestDTO dto,String email,int resortId);
}
