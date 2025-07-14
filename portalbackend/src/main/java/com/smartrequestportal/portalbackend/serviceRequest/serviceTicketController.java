package com.smartrequestportal.portalbackend.serviceRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public class serviceTicketController {
    private final serviceTicketRepository repo;

    public serviceTicketController(serviceTicketRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<serviceTicket> getFilteredRequests(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String technician) {

        return repo.filter(status, category, technician);
    }

    @GetMapping("/{id}")
    public ResponseEntity<serviceTicket> getById(@PathVariable Long id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
