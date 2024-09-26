package com.hospital.service;

import java.util.List;

import com.hospital.model.Appointment;

public interface AppointmentService {
	
	public List<Appointment> ListAllAppointment();
	
	public List<Appointment> ListAllAppointmentByDoctorID(Integer id);
	
	public Appointment GetAppointmentByID(Integer id);
	
	public void SaveData(Appointment appointment);
	
	public void Delete(Integer id);
	
	public Integer GetPatientIDByAppID(Integer id);
	
}
