package com.snapnote.service;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class OcrService {

    public String extractText(String imagePath) {
        Tesseract tesseract = new Tesseract();

        // macOS 기본 경로 (변경 가능)
        tesseract.setDatapath("/opt/homebrew/Cellar/tesseract/5.5.0_1/share/tessdata"); // 📌 경로 확인 필요
        tesseract.setLanguage("kor+eng");

        try {
            return tesseract.doOCR(new File(imagePath));
        } catch (TesseractException e) {
            e.printStackTrace();
            return "OCR 실패: " + e.getMessage();
        }
    }
}
