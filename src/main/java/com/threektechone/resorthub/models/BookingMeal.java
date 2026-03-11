package com.threektechone.resorthub.models;

import java.time.LocalDate;

import com.threektechone.resorthub.enums.MealTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "booking_meals")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class BookingMeal {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="meal_id")
    private int mealId;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    @Column(name="meal_time")
    private MealTime mealTime;

    private int quantity;

    @ManyToOne
    @JoinColumn(name="booking_id")
    private Booking booking;

    @ManyToOne
    @JoinColumn(name="menu_id")
    private ResortMenu resortMenu;
    
}
