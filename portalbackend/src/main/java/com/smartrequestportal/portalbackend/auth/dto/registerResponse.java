package com.smartrequestportal.portalbackend.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record registerResponse(@NotBlank String response) {
}
