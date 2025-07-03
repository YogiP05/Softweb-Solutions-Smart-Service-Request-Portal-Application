package com.smartrequestportal.portalbackend.auth;

import com.smartrequestportal.portalbackend.auth.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class authController {
    private final authService authService;

    public authController(authService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<loginResponse> login(@RequestBody loginRequest loginRequest) {
        loginResponse loginResponse = authService.login(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<registerResponse> register(@RequestBody registerRequest registerRequest) {
        registerResponse registerResponse = authService.register(registerRequest);
        return ResponseEntity.ok(registerResponse);
    }

    @PostMapping("/request-reset")
    public ResponseEntity<forgotPasswordResponse> requestReset(@RequestBody forgotPasswordRequest request) {
        try {
            String question = authService.getSecurityQuestion(request.email());
            return ResponseEntity.ok(new forgotPasswordResponse(question));
        } catch (Exception e) {
            System.out.println("Password reset attempt failed for: " + request.email());
            return ResponseEntity.ok(new forgotPasswordResponse
                    ("If your account exists, you'll be guided to the next step"));
        }
    }

    @PostMapping("/verify-answer")
    public ResponseEntity<answerVerificationResponse> verifyAnswer(@RequestBody securityQuestionRequest request) {
        try {
            Boolean success = authService.validateSecurityQuestion(request.email(), request.answer());
            return ResponseEntity.ok(new answerVerificationResponse(success));
        } catch (Exception e) {
            System.out.println("Password reset attempt failed for: " + request.email());
            return ResponseEntity.ok(new answerVerificationResponse(Boolean.FALSE));
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody resetPasswordRequest request) {
        try {
            authService.resetPassword(request.email(), request.newPassword());
        } catch (Exception e) {
            System.out.println("Password reset attempt failed for: " + request.email());
        }
        return ResponseEntity.ok("Password reset successful");
    }
}