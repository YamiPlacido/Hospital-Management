package com.hospital.service;

import com.hospital.model.Examination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class ConcurrencyService {
    @Autowired
    PDFExportService pdfExportService;
    boolean ok;

    public boolean createExaminationRequestPDF(Examination ex, String fileName){
        ok = false;
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Runnable runnable = () -> {
            System.out.println("Inside : " + Thread.currentThread().getName());

            ok = pdfExportService.createExaminationRequestPDF(ex,fileName);
        };
        executorService.submit(runnable);
        executorService.shutdown();
        return ok;
    }

}
