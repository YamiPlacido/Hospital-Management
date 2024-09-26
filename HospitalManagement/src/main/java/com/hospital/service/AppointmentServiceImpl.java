package com.hospital.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hospital.model.Appointment;
import com.hospital.model.Illness;
import com.hospital.model.IllnessType;
import com.hospital.repository.AppointmentRepository;
import com.hospital.repository.IllnessRepository;
import com.hospital.repository.IllnessTypeRepository;

@Component
public class AppointmentServiceImpl implements AppointmentService {

	@Autowired
	AppointmentRepository repo;

	@Override
	public List<Appointment> ListAllAppointment() {
		List<Appointment> listApp = (List<Appointment>) repo.findAll();
		return listApp;
	}

	@Override
	public Appointment GetAppointmentByID(Integer id) {
		Appointment app = repo.findById(id).get();
		return app;
	}

	@Override
	public void SaveData(Appointment app) {
		repo.save(app);
	}

	@Override
	public void Delete(Integer id) {
		repo.deleteById(id);
	}

	@Override
	public List<Appointment> ListAllAppointmentByDoctorID(Integer id) {
		List<Appointment> listApp = (List<Appointment>) repo.GetAllAppByDoctorID(id);
		return listApp;
	}

	@Override
	public Integer GetPatientIDByAppID(Integer id) {
		Integer PatientID = repo.GetPatientIDByAppID(id);
		return PatientID;
	}



}
