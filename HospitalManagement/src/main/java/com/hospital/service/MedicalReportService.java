package com.hospital.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hospital.dto.AppointmentDto;
import com.hospital.model.Appointment;
import com.hospital.model.Diagnosi;
import com.hospital.model.Illness;
import com.hospital.model.PrescriptionMedicine;
import com.hospital.model.Treatment;

import fr.opensagres.xdocreport.converter.ConverterTypeTo;
import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;
import fr.opensagres.xdocreport.template.formatter.FieldsMetadata;

@Service
public class MedicalReportService {
	public void MedicalReport(Appointment app, List<Diagnosi> diag, List<Treatment> treat, List<PrescriptionMedicine> pres) throws IOException, XDocReportException{
		InputStream in = new FileInputStream(new File("upload/TemplateMedicalReport.docx"));
		IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity);

		FieldsMetadata metadata = new FieldsMetadata();
		metadata.addFieldAsList("allillness.Illness.Name");
		metadata.addFieldAsList("allillness.Illness.Description");
		metadata.addFieldAsList("allillness.Degree");
		
		metadata.addFieldAsList("alltreat.TreatmentMethod.Name");
		metadata.addFieldAsList("alltreat.TreatmentMethod.Detail");
		metadata.addFieldAsList("alltreat.Illness.Name");
		metadata.addFieldAsList("alltreat.FollowupDate");
		
		metadata.addFieldAsList("allpres.Medicine.Name");
		metadata.addFieldAsList("allpres.Quantity");
		metadata.addFieldAsList("allpres.Dosage");

		report.setFieldsMetadata(metadata);

		IContext context = report.createContext();

		context.put("name", app.getPatient().getName());
		context.put("dob", app.getPatient().getDob());
		context.put("address", app.getPatient().getAddress());
		context.put("email", app.getPatient().getEmail());
		context.put("phone", app.getPatient().getPhone());

		context.put("allillness", diag);
		context.put("alltreat", treat);
		context.put("allpres", pres);
		
		Diagnosi d = diag.get(0);
		
		Options options = Options.getTo(ConverterTypeTo.PDF);
		OutputStream out = new FileOutputStream(new File("upload/MedicalReportOfApp"+d.getAppointment().getAppId()+".pdf"));
		report.convert(context, options, out);
	}
}
