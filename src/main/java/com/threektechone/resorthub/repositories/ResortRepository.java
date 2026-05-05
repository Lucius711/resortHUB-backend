package com.threektechone.resorthub.repositories;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.threektechone.resorthub.enums.ResortStatus;
import com.threektechone.resorthub.enums.ResortType;
import com.threektechone.resorthub.models.Resort;
import com.threektechone.resorthub.repositories.projection.ResortStatusCountProjection;



public interface ResortRepository extends JpaRepository<Resort, Integer> {
    
   @Query("""
    SELECT r FROM Resort r
    WHERE r.owner.email = :email 
    AND (
         :searchkey IS NULL OR :searchkey = '' OR
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
    AND r.step = 'CONTRACT_SIGN' 
    AND (
         :searchkey IS NULL OR :searchkey = '' OR
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

  @Query("""
SELECT r FROM Resort r
WHERE r.status = 'ACTIVE'
AND (
    :searchkey IS NULL OR :searchkey = '' OR
    LOWER(r.name) LIKE LOWER(CONCAT('%', :searchkey, '%')) OR
    LOWER(r.district) LIKE LOWER(CONCAT('%', :searchkey, '%')) OR
    LOWER(r.city) LIKE LOWER(CONCAT('%', :searchkey, '%'))
)
AND (:city IS NULL OR r.city = :city)
AND (:type IS NULL OR r.type = :type)
""")
Page<Resort> getPublicResorts(
    @Param("searchkey") String searchkey,
    @Param("city") String city,
    @Param("type") ResortType type,
    Pageable pageable
);

    @Query("""
    SELECT COUNT(r)
    FROM Resort r
    WHERE r.owner.email = :ownerEmail
    """)
    int getTotalResorts(@Param("ownerEmail") String ownerEmail);

    @Query("""
    SELECT COUNT(r)
    FROM Resort r
    WHERE r.owner.email = :ownerEmail
      AND r.status = 'ACTIVE'
    """)
    int getActiveResorts(@Param("ownerEmail") String ownerEmail);

    @Query("SELECT COUNT(r) FROM Resort r WHERE r.status = :status AND r.staff.email = :staffEmail")
    int countByStatusAndStaffEmail(@Param("status") ResortStatus status, @Param("staffEmail") String staffEmail);

    @Query("SELECT r.status as status, COUNT(r) as count " +
           "FROM Resort r " +
           "WHERE r.staff.email = :staffEmail " +
           "GROUP BY r.status")
    List<ResortStatusCountProjection> countResortsGroupByStatus(@Param("staffEmail") String staffEmail);



}
