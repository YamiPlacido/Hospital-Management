package com.hospital.service;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hospital.dto.AppointmentDto;
import com.hospital.dto.DoctorScheduleInforDto;
import com.hospital.dto.DoctorScheduleSearchDto;
import com.hospital.model.Appointment;
import com.hospital.model.Patient;
import com.hospital.model.Shift;

@Service
public interface AppointmentService {  

	 public List<Appointment> getAllAppointment();
	 
	 public List<Patient> getAllPatient();
	 
	 public Appointment getAppointmentById(long appId);
	 
	 public void saveOrUpdate(Appointment appointment);

	public void saveApp(AppointmentDto appointment);

	public List<AppointmentDto> getListAppointment();

	public List<Shift> getListShift(AppointmentDto appointment);

	public void exportSlip(long appId);

	public void disableAppointment(long appointmentId);

	public DoctorScheduleInforDto getDoctorSchedule(DoctorScheduleSearchDto doctorScheduleSearchDto);
	
	public List<Appointment> ListAllAppointment();
	
	public List<Appointment> ListAllAppointmentByDoctorID(Long id);
	
	public Appointment GetAppointmentByID(Long id );
	
	public void SaveData(Appointment appointment);
	
	public void Delete(Long id );
	
	public Integer GetPatientIDByAppID(Long id );
	 
}
