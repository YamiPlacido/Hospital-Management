package com.hospital.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.hospital.dto.ExaminationRequestTemplateDTO;
import com.hospital.model.Employee;
import com.hospital.model.Examination;
import com.hospital.model.Patient;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@Service
public class PDFExportService {
    public static final String TEMPLATE_FOLDER = "upload" + File.separator + "pdf_template"+ File.separator;
    public static final String EXPORT_FOLDER = "upload"+ File.separator+"pdf"+ File.separator+"examination"+ File.separator;
    ExaminationRequestTemplateDTO examRequest;
    public boolean createExaminationRequestPDF(Examination ex, String fileName){
        examRequest = setProperties(ex);
        return createPDF(examRequest,fileName,"ExaminationRequestTemplateA5.docx");
    }

    public boolean createExaminationResultPDF(Examination ex, String fileName,String content,String result){
        examRequest = setProperties(ex);
        examRequest.setContent(content);
        examRequest.setResult(result);
        return createPDF(examRequest,fileName,"ExaminationResultTemplate.docx");
    }

    public boolean createPDF(Object object, String fileName, String template){
        boolean ok = false;
        try {
            // 1) Load ODT file and set Velocity template engine and cache it to the
            // registry
            InputStream in = new FileInputStream(new File(TEMPLATE_FOLDER+template));
            IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity);

            // 2) Create Java model context
            IContext context = report.createContext();
            context.put("examRequest", object);

            // 3) Generate report by merging Java model with the ODT
            Options options = Options.getFrom(DocumentKind.DOCX).to(ConverterTypeTo.PDF);

            // 2) Get the converter from the registry
            IConverter converter = ConverterRegistry.getRegistry().getConverter(options);
            OutputStream out = new FileOutputStream(new File(EXPORT_FOLDER+"result1.docx"));
            report.process(context, out);
            InputStream indocx= new FileInputStream(new File(EXPORT_FOLDER+"result1.docx"));
            OutputStream outpdf = new FileOutputStream(new File(EXPORT_FOLDER+fileName));
            converter.convert(indocx, outpdf, options);

            System.out.println("Export done.");
            ok = true;
        } catch (IOException e) {
            ok = false;
            e.printStackTrace();
        } catch (XDocReportException e) {
            ok = false;
            e.printStackTrace();
        }
        return ok;
    }


    private ExaminationRequestTemplateDTO  setProperties(Examination ex){
        examRequest = new ExaminationRequestTemplateDTO();
        examRequest.setExaminationId(ex.getId());
        examRequest.setExaminationName(ex.getExaminationType().getName());
        Employee doctor = ex.getAppointment().getEmployee();
        examRequest.setDoctorName(doctor.getFirstName()+" "+doctor.getLastName());
        Patient patient = ex.getPatient();
        examRequest.setPatientId(patient.getPatientId());
        examRequest.setPatientName(patient.getName());
        Calendar cal = Calendar.getInstance();
        cal.setTime(patient.getDob());
        int bornYear = cal.get(Calendar.YEAR);
        cal.setTime(new Date());
        int nowYear = cal.get(Calendar.YEAR);
        examRequest.setPatientAge(nowYear-bornYear);
        return examRequest;
    }
}
