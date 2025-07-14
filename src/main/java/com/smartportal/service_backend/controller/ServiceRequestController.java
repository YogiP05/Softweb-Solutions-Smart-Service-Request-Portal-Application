package com.smartportal.service_backend.controller;

import com.smartportal.service_backend.model.ServiceRequest;
import com.smartportal.service_backend.service.ServiceRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ServiceRequestController {

    @Autowired
    private ServiceRequestService service;

    @PostMapping("/requests")
    public ResponseEntity<?> createRequest(@RequestBody ServiceRequest request) {
        try {
            ServiceRequest saved = service.createRequest(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping("/requests/{id}")
    public ResponseEntity<?> updateRequest(@PathVariable Long id, @RequestBody ServiceRequest updated) {
        Optional<ServiceRequest> existing = service.getRequestById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Request not found");
        }

        ServiceRequest saved = service.updateRequest(existing.get(), updated);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/requests")
    public ResponseEntity<?> getAllRequests() {
        return ResponseEntity.ok(service.getAllRequests());
    }

    @PatchMapping("/requests/{id}/assign")
    public ResponseEntity<?> assignTechnician(
            @PathVariable Long id,
            @RequestParam String technician) {
        try {
            ServiceRequest updated = service.assignTechnician(id, technician);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/requests/{id}/assigned")
    public ResponseEntity<?> getAssignedTechnician(@PathVariable Long id) {
        Optional<ServiceRequest> request = service.getRequestById(id);
        if (request.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Request not found");
        }
        return ResponseEntity.ok(request.get().getAssignedTo());
    }

    // New endpoint to update status only
    @PutMapping("/requests/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String newStatus = body.get("status");
        Optional<ServiceRequest> requestOpt = service.getRequestById(id);

        if (requestOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Request not found");
        }

        ServiceRequest request = requestOpt.get();
        request.setStatus(newStatus);
        request.setLastModifiedAt(LocalDateTime.now());

        return ResponseEntity.ok(service.save(request));
    }
}
