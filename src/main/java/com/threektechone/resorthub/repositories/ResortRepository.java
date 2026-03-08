package com.threektechone.resorthub.repositories;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.threektechone.resorthub.enums.ResortStatus;
import com.threektechone.resorthub.models.Resort;



public interface ResortRepository extends JpaRepository<Resort, Integer> {
    
   @Query("""
    SELECT r FROM Resort r
    WHERE r.owner.email = :email 
    AND (:searchkey IS NULL OR 
         LOWER(r.resortCode) LIKE LOWER(CONCAT('%', :searchkey, '%')) 
         OR LOWER(r.name) LIKE LOWER(CONCAT('%', :searchkey, '%')))
    AND (:status IS NULL OR r.status = :status)
    """)
    Page<Resort> getOwnerResorts(
        @Param("email") String email,
        @Param("searchkey") String searchkey,
        @Param("status") ResortStatus status,
        Pageable pageable
    );

    List<Resort> findByStatus(ResortStatus status);

    @Query("""
    SELECT r FROM Resort r
    WHERE r.status <> 'DRAFT'
    AND (:searchkey IS NULL OR 
         LOWER(r.resortCode) LIKE LOWER(CONCAT('%', :searchkey, '%')) 
         OR LOWER(r.name) LIKE LOWER(CONCAT('%', :searchkey, '%'))
         OR LOWER(r.owner.fullName) LIKE LOWER(CONCAT('%', :searchkey, '%')))
    AND (:status IS NULL OR r.status = :status)
    """)
    Page<Resort> getRegisterResorts(
        @Param("searchkey") String searchkey,
        @Param("status") ResortStatus status,
        Pageable pageable
    );
}
