package com.smartrequestportal.portalbackend.auth;

import com.smartrequestportal.portalbackend.User.User;
import com.smartrequestportal.portalbackend.User.userRepository;
import com.smartrequestportal.portalbackend.auth.dto.loginRequest;
import com.smartrequestportal.portalbackend.auth.dto.loginResponse;
import com.smartrequestportal.portalbackend.auth.dto.registerRequest;
import com.smartrequestportal.portalbackend.auth.dto.registerResponse;
import com.smartrequestportal.portalbackend.security.jwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class authService {
    private final userRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final jwtService jwtService;

    public authService(userRepository userRepo, PasswordEncoder encoder, jwtService jwtService) {
        this.userRepo = userRepo;
        this.passwordEncoder = encoder;
        this.jwtService = jwtService;
    }

    public loginResponse login(loginRequest loginRequest) {
        Optional<User> optionalUser = userRepo.findByEmail(loginRequest.email());

        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = optionalUser.get();

        if (!user.getActive()) {
            throw new RuntimeException("User is not active. Please try again.");
        }

        if (!passwordEncoder.matches(loginRequest.password(), user.getHashedPassword())) {
            throw new RuntimeException("Invalid username/email or password");
        }

        String token = jwtService.generateToken(user);

        return new loginResponse(token, user.getRole(), user.getEmail());
    }

    public registerResponse register(registerRequest registerRequest) {
        if (userRepo.findByEmail(registerRequest.email()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        User user = new User();
        user.setUsername(registerRequest.email());
        user.setEmail(registerRequest.email());
        user.setActive(true);
        user.setHashedPassword(passwordEncoder.encode(registerRequest.password()));
        user.setSecurityQuestion(registerRequest.securityQuestion());
        user.setSecurityAnswerHashed(registerRequest.securityAnswer());
        userRepo.save(user);

        return new registerResponse("Your user has been registered");
    }

    public String getSecurityQuestion(String email) {
        Optional<User> optionalUser = userRepo.findByEmail(email);

        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = optionalUser.get();

        return user.getSecurityQuestion();
    }

    public boolean validateSecurityQuestion(String email, String answer) {
        Optional<User> optionalUser = userRepo.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        User user = optionalUser.get();
        return passwordEncoder.matches(answer, user.getHashedPassword());
    }

    public void resetPassword(String email, String newPassword) {
        Optional<User> optionalUser = userRepo.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        User user = optionalUser.get();
        user.setHashedPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);
    }
}
