package com.example.demo.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeminiService {
    private static final String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent";  // Replace with Gemini API URL
    private static final String GEMINI_API_KEY = "";  // Replace with your API Key
    RestTemplate restTemplate = new RestTemplate();

    // Method to call the Gemini API and get the result
    public String getGeminiResponse(String prompt) {

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + GEMINI_API_KEY);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Construct the JSON body for the API call
        String requestBody = "{\n" +
                "  \"model\": \"gemini-2.5\",\n" +  // You can use gemini-2.0, gemini-2.5 or any available model
                "  \"messages\": [{" +
                "    \"role\": \"system\", \"content\": \"You are a professional assistant capable of extracting structured information from CVs.\"},\n" +
                "                {" +
                "    \"role\": \"user\", \"content\": \"" + prompt + "\"}]\n" +
                "}";

        // Wrap the request body in an HttpEntity
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // Send POST request to Gemini API
        ResponseEntity<String> response = restTemplate.exchange(GEMINI_API_URL, HttpMethod.POST, entity, String.class);

        return response.getBody();  // Return the JSON response body from Gemini API
    }

    // Method to call the Gemini API and get the result
    public String extractDetailsFromCV2(String prompt) {

        // Define the new prompt that requests specific details in JSON format
        String requestBody = "{\n" +
                "  \"contents\": [\n" +
                "    {\n" +
                "      \"parts\": [\n" +
                "        {\n" +
                "          \"text\": \"Extract the following details from the CV text and return them in JSON format in Vietnamese:\\n\" +\n" +
                "          \"- Họ và tên (Name)\\n\" +\n" +
                "          \"- Email\\n\" +\n" +
                "          \"- Số điện thoại (Phone)\\n\" +\n" +
                "          \"- Năm kinh nghiệm (Years of Experience)\\n\" +\n" +
                "          \"- Trường (School)\\n\" +\n" +
                "          \"- Vị trí (Position)\\n\" +\n" +
                "          \"- Pick 1 from these roles: QA, BA, PM, TEST, DEV, SA\\n\" +\n" +
                "          \"- Tổng quan (Summary)\\n\\n\" +\n" +
                "          \"Please generate the 'Tổng quan' (Summary) in Vietnamese, describing the most unique feature and skill of the candidate's experience and qualifications.\" +\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        // Set up the headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Prepare the request entity
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // Send a POST request to Gemini API, including the API key in the query string
        String urlWithApiKey = GEMINI_API_URL + "?key=" + GEMINI_API_KEY;

        // Send the request and get the response
        ResponseEntity<String> response = restTemplate.exchange(urlWithApiKey, HttpMethod.POST, entity, String.class);

        // Return the response body
        return response.getBody();
    }

    public String extractDetailsFromCV(String prompt) {
        System.out.println("Prompt is " + prompt);

        // Define the new prompt with proper escaping and JSON formatting
        String requestBody = "{\n" +
                "  \"contents\": [\n" +
                "    {\n" +
                "      \"parts\": [\n" +
                "        {\n" +
                "          \"text\": \"Extract the following details from the CV text and return them in JSON format in Vietnamese:\\n\\n" +
                "          - Họ và tên (Name)\\n" +
                "          - Email\\n" +
                "          - Số điện thoại (Phone)\\n" +
                "          - Năm kinh nghiệm (Years of Experience)\\n" +
                "          - Trường (School)\\n" +
                "          - Vị trí (Position)\\n" +
                "          - Pick 1 from these roles: QA, BA, PM, TEST, DEV, SA\\n" +
                "          - Tổng quan (Summary)\\n\\n" +
                "          Please generate the 'Tổng quan' (Summary) in Vietnamese, describing the most unique feature and skill of the candidate's experience and qualifications.\\n\\n" +
                "          The CV text will be as follows: \\\"" + prompt.replaceAll("\"", "\\\"") + "\\\"\"\n" +  // Properly escape double quotes in the prompt
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        // Set up the headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Prepare the request entity
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // Send a POST request to Gemini API, including the API key in the query string
        String urlWithApiKey = GEMINI_API_URL + "?key=" + GEMINI_API_KEY;

        // Send the request and get the response
        ResponseEntity<String> response = restTemplate.exchange(urlWithApiKey, HttpMethod.POST, entity, String.class);

        // Return the response body
        return response.getBody();
    }
}
