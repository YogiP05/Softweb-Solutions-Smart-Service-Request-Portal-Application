package com.smartrequestportal.portalbackend.repository;

import com.smartrequestportal.portalbackend.model.ServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ServiceRequestRepository extends JpaRepository<ServiceRequest, Long> {
    List<ServiceRequest> findByTitleAndDescriptionAndCreatedAtAfter(String title, String description, LocalDateTime after);

    List<ServiceRequest> findByStatus(String status);
    List<ServiceRequest> findByCategory(String category);
    List<ServiceRequest> findByUserId(String userId);
    List<ServiceRequest> findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String titleKeyword, String descriptionKeyword);
    List<ServiceRequest> findByCreatedAtAfter(LocalDateTime after);


    @Query("SELECT s FROM ServiceRequest s WHERE " +
            "(:status IS NULL OR s.status = :status) AND " +
            "(:category IS NULL OR s.category = :category) AND " +
            "(:technician IS NULL OR s.assignedTo = :technician)")
    List<ServiceRequest> filterDashboard(
            @Param("status") String status,
            @Param("category") String category,
            @Param("technician") String technician
    );
}
