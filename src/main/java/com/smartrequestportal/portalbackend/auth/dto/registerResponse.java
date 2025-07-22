package com.smartrequestportal.portalbackend.auth.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record registerResponse(@Valid @NotBlank String response) {
}
