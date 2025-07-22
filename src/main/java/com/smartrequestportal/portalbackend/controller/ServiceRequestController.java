package com.smartrequestportal.portalbackend.controller;

import com.smartrequestportal.portalbackend.model.ServiceRequest;
import com.smartrequestportal.portalbackend.service.ServiceRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ServiceRequestController {

    @Autowired
    private ServiceRequestService serviceRequestService;

    @PostMapping("/requests")
    public ResponseEntity<?> createRequest(@RequestBody ServiceRequest request) {
        try {
            ServiceRequest saved = serviceRequestService.createRequest(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping("/requests/{id}")
    public ResponseEntity<?> updateRequest(@PathVariable Long id, @RequestBody ServiceRequest updated) {
        Optional<ServiceRequest> existing = serviceRequestService.getRequestById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Request not found");
        }

        ServiceRequest saved = serviceRequestService.updateRequest(existing.get(), updated);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/requests")
    public ResponseEntity<?> getAllRequests() {
        return ResponseEntity.ok(serviceRequestService.getAllRequests());
    }

    @PatchMapping("/requests/{id}/assign")
    public ResponseEntity<?> assignTechnician(
            @PathVariable Long id,
            @RequestParam String technician) {
        try {
            ServiceRequest updated = serviceRequestService.assignTechnician(id, technician);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/requests/{id}/assigned")
    public ResponseEntity<?> getAssignedTechnician(@PathVariable Long id) {
        Optional<ServiceRequest> request = serviceRequestService.getRequestById(id);
        if (request.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Request not found");
        }
        return ResponseEntity.ok(request.get().getAssignedTo());
    }

    // New endpoint to update status only
    @PutMapping("/requests/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String newStatus = body.get("status");
        Optional<ServiceRequest> requestOpt = serviceRequestService.getRequestById(id);

        if (requestOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Request not found");
        }

        ServiceRequest request = requestOpt.get();
        request.setStatus(newStatus);
        request.setLastModifiedAt(LocalDateTime.now());

        return ResponseEntity.ok(serviceRequestService.save(request));
    }

    @GetMapping("/requests/filter")
    public ResponseEntity<?> filterRequests(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String technician
    ) {
        return ResponseEntity.ok(serviceRequestService.filterRequests(status, category, technician));
    }

    @GetMapping("/requests/search")
    public ResponseEntity<?> searchRequests(@RequestParam String title) {
        return ResponseEntity.ok(serviceRequestService.searchByTitleOrDescription(title));
    }

    @GetMapping("/requests/{id}")
    public ResponseEntity<?> getRequestById(@PathVariable Long id) {
        Optional<ServiceRequest> request = serviceRequestService.getRequestById(id);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Request not found"));
    }

    @GetMapping("/requests/recent")
    public ResponseEntity<?> getRecentRequests(@RequestParam String after) {
        LocalDateTime afterTime = LocalDateTime.parse(after); // Must send ISO 8601 from frontend
        return ResponseEntity.ok(serviceRequestService.getRecentRequests(afterTime));
    }

}
