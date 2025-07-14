package com.smartportal.service_backend.service;

import com.smartrequestportal.portalbackend.repository.ServiceRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceRequestService {

    @Autowired
    private ServiceRequestRepository repository;

    public ServiceRequest createRequest(ServiceRequest request) {
        List<ServiceRequest> recent = repository.findByTitleAndDescriptionAndCreatedAtAfter(
                request.getTitle(), request.getDescription(), LocalDateTime.now().minusMinutes(1)
        );

        if (!recent.isEmpty()) {
            throw new IllegalArgumentException("Duplicate request detected within 1 minute.");
        }

        request.setCreatedAt(LocalDateTime.now());
        return repository.save(request);
    }

    public Optional<ServiceRequest> getRequestById(Long id) {
        return repository.findById(id);
    }

    public ServiceRequest updateRequest(ServiceRequest existing, ServiceRequest updated) {
        existing.setTitle(updated.getTitle());
        existing.setDescription(updated.getDescription());
        existing.setStatus(updated.getStatus());
        existing.setCategory(updated.getCategory());
        existing.setLastModifiedAt(LocalDateTime.now());
        return repository.save(existing);
    }

    public ServiceRequest assignTechnician(Long id, String technician) {
        Optional<ServiceRequest> optional = repository.findById(id);
        if (optional.isEmpty()) {
            throw new IllegalArgumentException("Request not found");
        }
        ServiceRequest request = optional.get();
        request.setAssignedTo(technician);
        request.setLastModifiedAt(LocalDateTime.now());
        return repository.save(request);
    }

    public List<ServiceRequest> getAllRequests() {
        return repository.findAll();
    }

    public void deleteRequest(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Request not found");
        }
        repository.deleteById(id);
    }

    public ServiceRequest save(ServiceRequest request){
        return repository.save(request);
    }
}
