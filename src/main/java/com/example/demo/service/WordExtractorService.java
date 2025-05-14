package com.example.demo.service;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class WordExtractorService {
    // Method to extract text from the Word document
    public String extractTextFromWord(MultipartFile file) throws IOException {
        // Get the input stream of the Word file
        InputStream fileInputStream = file.getInputStream();
        XWPFDocument document = new XWPFDocument(fileInputStream);

        StringBuilder extractedText = new StringBuilder();

        // Extract text from all paragraphs
        List<XWPFParagraph> paragraphs = document.getParagraphs();
        for (XWPFParagraph paragraph : paragraphs) {
            extractedText.append(paragraph.getText()).append("\n");
        }

//        document.clone();
        return extractedText.toString();
    }
}
