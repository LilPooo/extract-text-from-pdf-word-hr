package com.example.demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class ChatGptService {
    private static final String OPENAI_API_KEY = "";
    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";

//    public String getChatGptResponse(String prompt) {
//        RestTemplate restTemplate = new RestTemplate();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", "Bearer " + OPENAI_API_KEY);
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        // Construct the request body with the user prompt
//        String requestBody = "{\n" +
//                "  \"model\": \"gpt-3.5-turbo\",\n" +
//                "  \"messages\": [{" +
//                "    \"role\": \"system\", \"content\": \"You are a helpful assistant.\"},\n" +
//                "                {" +
//                "    \"role\": \"user\", \"content\": \"" + prompt + "\"}]\n" +
//                "}";
//
//        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
//        ResponseEntity<String> response = restTemplate.exchange(OPENAI_API_URL, HttpMethod.POST, entity, String.class);
//
//        return response.getBody();  // Return the response as String (JSON)
//    }

    public String getChatGptResponse(String prompt) {
        RestTemplate restTemplate = new RestTemplate();

        // Set up headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + OPENAI_API_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create the request body using a Map to handle JSON properly
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("messages", new Object[]{
                new HashMap<String, String>() {{
                    put("role", "system");
                    put("content", "You are a helpful assistant.");
                }},
                new HashMap<String, String>() {{
                    put("role", "user");
                    put("content", prompt);
                }}
        });

        // Convert the request body to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequestBody = null;
        try {
            jsonRequestBody = objectMapper.writeValueAsString(requestBody);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error converting request body to JSON";
        }

        // Send the request to the OpenAI API
        HttpEntity<String> entity = new HttpEntity<>(jsonRequestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(OPENAI_API_URL, HttpMethod.POST, entity, String.class);

        return response.getBody();  // Return the response as a string (JSON)
    }
}
