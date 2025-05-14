package com.example.demo.controller;

import com.example.demo.service.GeminiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gemini")
public class GeminiController {

    @Autowired
    private GeminiService geminiService;

    @PostMapping("/process-cv")
    public String processCV(@RequestBody String cvText) {
        // Send the extracted CV text to Gemini API for processing
        String prompt = "Extract the following details from the CV text and return them in JSON format in Vietnamese:\n" +
                "- Họ và tên (Name)\n" +
                "- Email\n" +
                "- Số điện thoại (Phone)\n" +
                "- Năm kinh nghiệm (Years of Experience)\n" +
                "- Trường (School)\n" +
                "- Cấp bậc (Level)\n" +
                "- Vị trí (Position)\n" +
                "- Tổng quan (Summary)\n\n" +
                "Here is the CV text:\n" + cvText + "\n\n" +
                "Please generate the 'Tổng quan' (Summary) in Vietnamese, describing the most unique feature and skill of the candidate's experience and qualifications.";

        // Call the Gemini service and get the response
        return geminiService.getGeminiResponse(prompt);
    }

}
