package com.threektechone.resorthub.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.threektechone.resorthub.enums.RequestStatus;
import com.threektechone.resorthub.models.EditResortRequest;


public interface EditResortRequestRepository extends JpaRepository<EditResortRequest,Integer > {

    @Query("""
    SELECT e FROM EditResortRequest e
    WHERE :status IS NULL OR e.requestStatus = :status
    """)
    Page<EditResortRequest> getEditRequests(
        @Param("status") RequestStatus status,
        Pageable pageable
    );
}
