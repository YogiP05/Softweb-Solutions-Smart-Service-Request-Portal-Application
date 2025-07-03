package com.smartrequestportal.portalbackend.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record registerRequest(@NotBlank(message = "Username is required")
                              @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
                              @Pattern(regexp = "^[a-zA-Z0-9._-]+$",
                                      message = "Username can only contain letters, numbers, dots, underscores, and hyphens")
                              String username,

                              @NotBlank(message = "Password is required")
                              @Size(min = 8, max = 128, message = "Password must be between 8 and 128 characters")
                              @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
                                      message = "Password must contain at least one lowercase letter, one uppercase letter, one digit, and one special character (@$!%*?&)")
                              String password,

                              @NotBlank(message = "Email is requried")
                              @Email(message = "Please provide a valid email address")
                              @Size(max = 100, message = "Email must not exceed 100 characters")
                              String email,

                              @NotBlank(message = "Security question is required")
                              @Size(min = 10, max = 200, message = "Security question must be between 10 and 200 characters")
                              String securityQuestion,

                              @NotBlank(message = "Security answer is required")
                              @Size(min = 2, max = 100, message = "Security answer must be between 2 and 100 characters")
                              String securityAnswer
) {}
