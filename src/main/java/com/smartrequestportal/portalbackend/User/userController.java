package com.smartrequestportal.portalbackend.User;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class userController {
    private final userRepository userRepository;

    public userController(userRepository userRepository) {
        this.userRepository = userRepository;
    }


}
