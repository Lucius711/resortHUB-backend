package com.threektechone.resorthub.repositories;
import java.time.LocalDateTime;
import java.util.List;
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
import com.threektechone.resorthub.repositories.projection.UserChartProjection;
import com.threektechone.resorthub.repositories.projection.UserRoleCountProjection;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    Boolean existsByPhone(String phone);
    
    @Query("""
    SELECT u FROM User u
    JOIN u.role r
    WHERE 
        (:search IS NULL OR :search = '' OR
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

    @Query("SELECT COUNT(u) FROM User u")
    int countTotalUsers();

    @Query("""
    SELECT COUNT(u) FROM User u
    WHERE u.createdAt >= :start AND u.createdAt < :end
    """)
    int countUsersByCreatedAtBetween(
        @Param("start") LocalDateTime start,
        @Param("end") LocalDateTime end
    );

    @Query("""
    SELECT r.roleName AS roleName, COUNT(u) AS count
    FROM User u
    JOIN u.role r
    GROUP BY r.roleName
    """)
    List<UserRoleCountProjection> countByRole();

    @Query("""
    SELECT CAST(u.createdAt AS date) AS date, COUNT(u) AS count
    FROM User u
    WHERE u.createdAt >= :start
    GROUP BY CAST(u.createdAt AS date)
    ORDER BY CAST(u.createdAt AS date)
    """)
    List<UserChartProjection> getUserChart(
        @Param("start") LocalDateTime start
    );

    @Query("""
    SELECT COUNT(u) FROM User u
    WHERE u.status = :status
    AND u.createdAt >= :start AND u.createdAt < :end
    """)
    int countActiveUsers(
       @Param("status") UserStatus status,
       @Param("start") LocalDateTime start,
       @Param("end") LocalDateTime end
    );
}
