package com.smartrequestportal.portalbackend.auth;

import com.smartrequestportal.portalbackend.auth.dto.loginRequest;
import com.smartrequestportal.portalbackend.auth.dto.loginResponse;
import com.smartrequestportal.portalbackend.auth.dto.registerRequest;
import com.smartrequestportal.portalbackend.auth.dto.registerResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
public class authController {
    private final authService authService;

    public authController(authService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<loginResponse> login(loginRequest loginRequest) {
        loginResponse loginResponse = authService.login(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<registerResponse> register(registerRequest registerRequest) {
        registerResponse registerResponse = authService.register(registerRequest);
        return ResponseEntity.ok(registerResponse);
    }

}
