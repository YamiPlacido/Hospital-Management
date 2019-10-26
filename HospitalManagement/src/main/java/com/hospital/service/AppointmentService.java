package com.hospital.service;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hospital.dto.AppointmentDto;
import com.hospital.dto.DoctorScheduleInforDto;
import com.hospital.dto.DoctorScheduleSearchDto;
import com.hospital.model.Appointment;
import com.hospital.model.Patient;
import com.hospital.model.Shift;

import fr.opensagres.xdocreport.core.XDocReportException;

@Service
public interface AppointmentService {  

	 public List<Appointment> getAllAppointment();
	 
	 public List<Patient> getAllPatient();
	 
	 public Appointment getAppointmentById(long appId);
	 
	 public void saveOrUpdate(Appointment appointment);

	public void saveApp(AppointmentDto appointment);

	public List<AppointmentDto> getListAppointment();

	public List<Shift> getListShift(AppointmentDto appointment);

	public void exportSlip(Long appId);

	public void disableAppointment(Long appointmentId);

	public DoctorScheduleInforDto getDoctorSchedule(DoctorScheduleSearchDto doctorScheduleSearchDto);
	
	public List<Appointment> ListAllAppointment();
	
	public List<Appointment> ListAllAppointmentByDoctorID(Long id);
	
	public Appointment GetAppointmentByID(Long id );
	
	public void SaveData(Appointment appointment);
	
	public void Delete(Long id );
	
	public Long GetPatientIDByAppID(Long id );

	public void exportSchedule(Long employeeId, Date dateFrom) throws IOException, XDocReportException;
	 
}
