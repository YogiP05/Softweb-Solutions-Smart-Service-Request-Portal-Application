package com.smartrequestportal.portalbackend.serviceRequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface serviceTicketRepository extends JpaRepository<serviceTicket, Long> {

    // Basic finders (optional, in case you use them individually)
    List<serviceTicket> findByStatus(String status);
    List<serviceTicket> findByCategory(String category);
    List<serviceTicket> findByTechnician_Id(Long technicianId);

    // Flexible filter using optional parameters
    @Query("SELECT s FROM serviceTicket s WHERE " +
            "(:status IS NULL OR s.status = :status) AND " +
            "(:category IS NULL OR s.category = :category) AND " +
            "(:technicianId IS NULL OR s.technician.id = :technicianId)")
    List<serviceTicket> filter(@Param("status") String status,
                               @Param("category") String category,
                               @Param("technicianId") Long technicianId);
}
