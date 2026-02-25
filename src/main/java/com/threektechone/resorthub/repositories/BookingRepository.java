package com.threektechone.resorthub.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.threektechone.resorthub.models.Booking;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    
}
