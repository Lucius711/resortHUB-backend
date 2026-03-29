package com.threektechone.resorthub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.threektechone.resorthub.models.ResortReview;

public interface  ResortReviewRepository extends JpaRepository<ResortReview,Integer> {
    @Query("""
    SELECT COALESCE(AVG(rv.rating), 0)
    FROM ResortReview rv
    JOIN rv.resort r
    WHERE r.owner.email = :ownerEmail
    """)
    Double getAverageRating(@Param("ownerEmail") String ownerEmail);


    @Query("""
    SELECT COUNT(rv)
    FROM ResortReview rv
    JOIN rv.resort r
    WHERE r.owner.email = :ownerEmail
    """)
    int getTotalReviews(@Param("ownerEmail") String ownerEmail);
}