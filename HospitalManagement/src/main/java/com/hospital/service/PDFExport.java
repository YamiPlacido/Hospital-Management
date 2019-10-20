package com.hospital.service;

import com.hospital.entity.Examination;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import fr.opensagres.xdocreport.converter.ConverterRegistry;
import fr.opensagres.xdocreport.converter.ConverterTypeTo;
import fr.opensagres.xdocreport.converter.IConverter;
import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.core.document.DocumentKind;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class PDFExport {
    public void createPDF(){
        try {
            // 1) Load ODT file and set Velocity template engine and cache it to the
            // registry
            InputStream in = new FileInputStream(new File("upload/ExaminationTemplate.docx"));
            IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity);

            // 2) Create Java model context
            IContext context = report.createContext();
            context.put("name", "world");

            // 3) Generate report by merging Java model with the ODT
            Options options = Options.getFrom(DocumentKind.DOCX).to(ConverterTypeTo.PDF);

            // 2) Get the converter from the registry
            IConverter converter = ConverterRegistry.getRegistry().getConverter(options);
            OutputStream out = new FileOutputStream(new File("upload/result1.docx"));
            report.process(context, out);
            InputStream indocx= new FileInputStream(new File("upload/result1.docx"));
            OutputStream outpdf = new FileOutputStream(new File("upload/result2.pdf"));
            converter.convert(indocx, outpdf, options);

            System.out.println("Export done.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XDocReportException e) {
            e.printStackTrace();
        }
    }
}
