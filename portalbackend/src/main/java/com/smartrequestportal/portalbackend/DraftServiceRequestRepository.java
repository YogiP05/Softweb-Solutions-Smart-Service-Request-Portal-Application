package com.smartportal.service_backend.repository;

import com.smartportal.service_backend.model.DraftServiceRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DraftServiceRequestRepository extends JpaRepository<DraftServiceRequest, Long> {
    List<DraftServiceRequest> findByUserId(String userId);
}
