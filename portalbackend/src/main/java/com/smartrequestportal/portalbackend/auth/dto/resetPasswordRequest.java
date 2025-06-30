package com.smartrequestportal.portalbackend.auth.dto;

public record resetPasswordRequest(String email,
                                   String newPassword) {
}
