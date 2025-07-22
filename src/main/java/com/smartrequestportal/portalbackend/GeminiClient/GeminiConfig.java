package com.smartrequestportal.portalbackend.GeminiClient;

import com.google.genai.Client;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeminiConfig {
    public Client geminiClient() {
        return new Client();
    }


}
