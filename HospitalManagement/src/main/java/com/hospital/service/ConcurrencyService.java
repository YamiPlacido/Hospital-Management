package com.hospital.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class ConcurrencyService {
    @Autowired
    PDFExportService pdfExportService;

    public void createPDF(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Runnable runnable = () -> {
            System.out.println("Inside : " + Thread.currentThread().getName());
            pdfExportService.createPDF();
        };
        executorService.submit(runnable);
        executorService.shutdown();
    }

}
