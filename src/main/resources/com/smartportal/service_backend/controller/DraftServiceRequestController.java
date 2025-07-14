package com.smartportal.service_backend.controller;

import com.smartportal.service_backend.model.DraftServiceRequest;
import com.smartportal.service_backend.service.DraftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/requests")
@CrossOrigin(origins = "http://localhost:3000")
public class DraftServiceRequestController {

    @Autowired
    private DraftService draftService;

    @PostMapping
    public DraftServiceRequest createDraft(@RequestBody DraftServiceRequest request) {
        return draftService.saveDraft(request);
    }
}
