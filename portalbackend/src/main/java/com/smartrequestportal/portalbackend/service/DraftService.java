package com.smartportal.service_backend.service;


import com.smartportal.service_backend.model.DraftServiceRequest;
import com.smartportal.service_backend.repository.DraftServiceRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DraftService {

    @Autowired
    private DraftServiceRequestRepository draftRepo;

    public DraftServiceRequest saveDraft(DraftServiceRequest draft) {
        return draftRepo.save(draft);
    }

    public List<DraftServiceRequest> getDraftsByUserId(String userId) {
        return draftRepo.findByUserId(userId);
    }

    public void deleteDraft(Long id) {
        draftRepo.deleteById(id);
    }
}
