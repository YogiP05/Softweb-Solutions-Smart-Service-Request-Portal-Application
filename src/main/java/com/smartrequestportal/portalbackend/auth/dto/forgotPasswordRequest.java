package com.smartrequestportal.portalbackend.auth.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record forgotPasswordRequest(@NotBlank @Email String email) {
}
