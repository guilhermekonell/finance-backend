package com.finance.backend.services;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class ExtractService {

    public String extractDataFromPdf(MultipartFile file) {
        try {
            PDDocument pdfDocument = PDDocument.load(file.getInputStream());
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(pdfDocument);
            pdfDocument.close();
            return text;
        } catch (Exception e) {
            log.error("Error on extract data from PDF: {}", e.getMessage(), e);
            return "Error on extract data from PDF.";
        }
    }

}
