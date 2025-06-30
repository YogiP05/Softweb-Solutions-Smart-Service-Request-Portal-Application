package com.smartrequestportal.portalbackend.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record registerRequest(@NotBlank String username,
                              @NotBlank String password,
                              @NotBlank String email,
                              @NotBlank String securityQuestion,
                              @NotBlank String securityAnswer) {}
