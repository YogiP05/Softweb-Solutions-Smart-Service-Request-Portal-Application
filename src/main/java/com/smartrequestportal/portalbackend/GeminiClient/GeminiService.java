package com.smartrequestportal.portalbackend.GeminiClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class GeminiService {

    @Value("${GOOGLE_API_KEY}")
    private String apiKey;

    @Value("${GOOGLE_GEMINI_URL}")
    private String geminiUrl;

    public String generateContent(String prompt) throws IOException {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestJson = """
        {
          "contents": [
            {
              "parts": [
                {
                  "text": "%s"
                }
              ]
            }
          ]
        }
        """.formatted(prompt);

        HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                geminiUrl + apiKey,
                entity,
                String.class
        );

        return response.getBody();
    }

    public List<Integer> parseUrgencyScores(String response, int expectedCount) {
        List<Integer> scores = new ArrayList<>();

        Pattern pattern = Pattern.compile("(\\d{1,2})");
        Matcher matcher = pattern.matcher(response);

        while (matcher.find() && scores.size() < expectedCount) {
            scores.add(Integer.parseInt(matcher.group(1)));
        }

        while (scores.size() < expectedCount) {
            scores.add(5);
        }

        return scores;
    }
}