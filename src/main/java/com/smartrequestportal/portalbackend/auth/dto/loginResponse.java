package com.smartrequestportal.portalbackend.auth.dto;

import com.smartrequestportal.portalbackend.User.userRole;

public record loginResponse(String jwtToken, userRole role, String message) {
}
