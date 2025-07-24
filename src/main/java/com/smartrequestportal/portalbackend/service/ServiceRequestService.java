package com.smartrequestportal.portalbackend.service;

import com.smartrequestportal.portalbackend.GeminiClient.GeminiService;
import com.smartrequestportal.portalbackend.model.ServiceRequest;
import com.smartrequestportal.portalbackend.repository.ServiceRequestRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceRequestService {

    private final GeminiService geminiService;
    private ServiceRequestRepository repository;


    public ServiceRequestService(ServiceRequestRepository repository, GeminiService geminiService) {
        this.repository = repository;
        this.geminiService = geminiService;
    }

    public ServiceRequest createRequest(ServiceRequest request) throws IOException {
        // Prevent duplicates
        List<ServiceRequest> recent = repository.findByTitleAndDescriptionAndCreatedAtAfter(
                request.getTitle(), request.getDescription(), LocalDateTime.now().minusMinutes(1)
        );

        if (!recent.isEmpty()) {
            throw new IllegalArgumentException("Duplicate request detected within 1 minute.");
        }

        // Initialize new request
        request.setCreatedAt(LocalDateTime.now());
        request.setPriority(null);
        ServiceRequest saved = repository.save(request);

        // Build prompt
        StringBuilder promptBuilder = new StringBuilder("Rate the urgency of the following service requests from 1 to 10.\n\n");
        List<ServiceRequest> serviceRequests = getAllRequests();

        for (int i = 0; i < serviceRequests.size(); i++) {
            String desc = serviceRequests.get(i).getDescription();
            promptBuilder.append("Request ").append(i + 1).append(": ")
                    .append(desc != null ? desc : "No description provided").append("\n");
        }

        String prompt = promptBuilder.toString();

        // Get scores from Gemini
        String geminiResponse = geminiService.generateContent(prompt);
        List<Integer> scores = geminiService.parseUrgencyScores(geminiResponse, serviceRequests.size());

        if (scores.size() != serviceRequests.size()) {
            throw new RuntimeException("Mismatch between service requests and returned urgency scores");
        }

        // Update priorities
        List<ServiceRequest> toUpdate = new ArrayList<>();
        for (int i = 0; i < serviceRequests.size(); i++) {
            ServiceRequest sr = serviceRequests.get(i);
            Integer oldScore = sr.getPriority();
            int newScore = scores.get(i);

            if (oldScore == null || !oldScore.equals(newScore)) {
                sr.setPriority(newScore);
                sr.setLastModifiedAt(LocalDateTime.now());
                toUpdate.add(sr);
            }
        }

        repository.saveAll(toUpdate);

        return saved;
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

    public List<ServiceRequest> filterRequests(String status, String category, String assignedTo, Integer urgency) {
        return repository.filterDashboard(status, category, assignedTo, urgency);
    }

    public List<ServiceRequest> searchByTitleOrDescription(String keyword) {
        return repository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword);
    }

    public List<ServiceRequest> getRecentRequests(LocalDateTime after) {
        return repository.findByCreatedAtAfter(after);
    }



}
