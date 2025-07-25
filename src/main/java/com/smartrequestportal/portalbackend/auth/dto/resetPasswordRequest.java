package com.smartrequestportal.portalbackend.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record resetPasswordRequest(
                                    @NotBlank(message = "Email is requried")
                                    @Email(message = "Please provide a valid email address")
                                    @Size(max = 100, message = "Email must not exceed 100 characters")
                                    String email,

                                    @NotBlank(message = "Password is required")
                                    @Size(min = 8, max = 128, message = "Password must be between 8 and 128 characters")
                                    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
                                            message = "Password must contain at least one lowercase letter, one uppercase letter, one digit, and one special character (@$!%*?&)")
                                    String newPassword) {
}
