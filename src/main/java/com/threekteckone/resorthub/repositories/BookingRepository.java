package com.threekteckone.resorthub.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.threekteckone.resorthub.models.Booking;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    
}
