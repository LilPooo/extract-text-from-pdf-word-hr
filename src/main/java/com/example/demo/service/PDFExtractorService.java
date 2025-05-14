package com.example.demo.service;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class PDFExtractorService {
    public String extractTextFromPDF(InputStream pdfInputStream) throws IOException {
        PDDocument document = PDDocument.load(pdfInputStream);

        // Create PDFTextStripper to extract text
        PDFTextStripper pdfStripper = new PDFTextStripper();

        // Extract the text from the document
        String extractedText = pdfStripper.getText(document);

        // Close the document to release resources
        document.close();
        System.out.println("Extract successfully!");
        return extractedText;
    }
}
