package com.threektechone.resorthub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.threektechone.resorthub.models.BookingMeal;

public interface BookingMealRepository extends JpaRepository<BookingMeal, Integer> {
    
}
