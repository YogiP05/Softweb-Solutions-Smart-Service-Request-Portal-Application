package com.smartrequestportal.portalbackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.smartrequestportal.portalbackend.model.DraftServiceRequest;

import java.util.List;

@Repository
public interface DraftServiceRequestRepository extends JpaRepository<DraftServiceRequest, Long> {
    List<DraftServiceRequest> findByUserId(String userId);
}
