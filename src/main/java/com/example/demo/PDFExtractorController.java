package com.example.demo;


import com.example.demo.service.ChatGptService;
import com.example.demo.service.GeminiService;
import com.example.demo.service.PDFExtractorService;
import com.example.demo.service.WordExtractorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/pdf")
public class PDFExtractorController {

    @Autowired
    private PDFExtractorService pdfExtractorService;

    @Autowired
    private ChatGptService chatGptService;

    @Autowired
    private WordExtractorService wordExtractorService;

    @Autowired
    private GeminiService geminiService;


    @PostMapping("/extract-text")
    public ResponseEntity<String> extractTextFromPDF(@RequestParam("file") MultipartFile file) {
        try {
            // Get the InputStream of the uploaded PDF file
            String extractedText = "";
            if (getFileExtension(file).equals("pdf")) {
                extractedText = pdfExtractorService.extractTextFromPDF(file.getInputStream());
            } else if (getFileExtension(file).equals("docx")) {

                extractedText = wordExtractorService.extractTextFromWord(file);

            }

            System.out.println("Details information: " + geminiService.extractDetailsFromCV(extractedText));

//            String extractedText = pdfExtractorService.extractTextFromPDF(file.getInputStream());
            return ResponseEntity.ok(geminiService.extractDetailsFromCV(extractedText));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error extracting text from PDF: " + e.getMessage());
        }
    }

    @PostMapping("/process")
    public ResponseEntity<String> processCV(@RequestParam("file") MultipartFile file) {
        try {
            // Get the InputStream of the uploaded PDF file
            String extractedText = pdfExtractorService.extractTextFromPDF(file.getInputStream());
            String prompt = "Extract the following information from the CV and return it in JSON format:\n" +
                    "- Name\n- Email\n- Phone\n- Years of Experience (YOE)\n- School\n- Level\n- Position\n- Offer\n- Summary\n\n" +
                    "Here is the CV text:\n" + extractedText;

            String response = chatGptService.getChatGptResponse(prompt);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error extracting text from PDF: " + e.getMessage());
        }
    }

    private String getFileExtension(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
