package com.threektechone.resorthub.repositories;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.threektechone.resorthub.enums.RoleName;
import com.threektechone.resorthub.enums.UserStatus;
import com.threektechone.resorthub.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
    
    @Query("""
    SELECT u FROM User u
    JOIN u.role r
    WHERE 
        (:search IS NULL OR 
         LOWER(u.fullName) LIKE LOWER(CONCAT('%', :search, '%')) 
         OR LOWER(u.email) LIKE LOWER(CONCAT('%', :search, '%')))
    AND (:gender IS NULL OR u.gender = :gender)
    AND (:roleName IS NULL OR r.roleName = :roleName)
    AND (:status IS NULL OR u.status = :status)
    """)
    Page<User> getUserListWithSearchAndFilterAndPagination(
        @Param("search") String search,
        @Param("gender") Boolean gender,
        @Param("roleName") RoleName roleName,
        @Param("status") UserStatus status,
        Pageable pageable
    );
}
