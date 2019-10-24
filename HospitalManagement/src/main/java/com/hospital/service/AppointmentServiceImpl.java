package com.hospital.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hospital.dto.AppointmentDto;
import com.hospital.dto.DoctorScheduleDetailDto;
import com.hospital.dto.DoctorScheduleInforDto;
import com.hospital.dto.DoctorScheduleSearchDto;
import com.hospital.model.Appointment;
import com.hospital.model.Employee;
import com.hospital.model.Patient;
import com.hospital.model.Shift;
import com.hospital.repository.AppointmentRepository;
import com.hospital.repository.EmployeeRepository;
import com.hospital.repository.PatientRepository;

import fr.opensagres.xdocreport.core.XDocReportException;

@Service
@Transactional(readOnly = false)
public class AppointmentServiceImpl implements AppointmentService {

	@Autowired
	AppointmentRepository appointmentRepository;

	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	PatientRepository patientRepository;

	@Override
	public List<Patient> getAllPatient() {
		List<Patient> patientList = (List<Patient>) patientRepository.findAll();
		return patientList;
	}

	@Override
	public List<Appointment> getAllAppointment() {
		List<Appointment> appointmentList = (List<Appointment>) appointmentRepository.findAll();
		return appointmentList;
	}

	@Override
	public Appointment getAppointmentById(long appId) {
		return appointmentRepository.findById(appId).get();
	}

	@Override
	public void saveOrUpdate(Appointment appointment) {
		appointmentRepository.save(appointment);
	}

	@Override
	public void saveApp(AppointmentDto appointment) {
		appointmentRepository.save(appointmentRepository.saveApp(appointment));
	}

	public List<AppointmentDto> getListAppointment() {
		return appointmentRepository.getListAppointment();
	}

	public List<Shift> getListShift(AppointmentDto appointment) {
		return appointmentRepository.getListShiftAvailabelByDoctorId(appointment);

	}

	@Override
	public void exportSlip(long appId) {
		try {
			appointmentRepository.exportAppSlip(appId);
		} catch (IOException | XDocReportException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void disableAppointment(long appointmentId) {
		Appointment app = appointmentRepository.findById(appointmentId).get();
		app.setStatus(false);
		appointmentRepository.save(app);
	}

	@Override
	public DoctorScheduleInforDto getDoctorSchedule(DoctorScheduleSearchDto doctorScheduleSearchDto) {
		DoctorScheduleInforDto infor = new DoctorScheduleInforDto();
		Employee emp = employeeRepository.findById(doctorScheduleSearchDto.getEmployeeId()).get();
		infor.setEmployee(emp);
		List<DoctorScheduleDetailDto> listDetail = new ArrayList();
		List<DoctorScheduleDetailDto> listShift1 = new ArrayList();
		List<DoctorScheduleDetailDto> listShift2 = new ArrayList();
		List<DoctorScheduleDetailDto> listShift3 = new ArrayList();
		List<DoctorScheduleDetailDto> listShift4 = new ArrayList();
		List<DoctorScheduleDetailDto> listShift5 = new ArrayList();
		listDetail = appointmentRepository.getDoctorSchedule(doctorScheduleSearchDto);
		for (DoctorScheduleDetailDto d : listDetail) {
			Calendar c = Calendar.getInstance();
			c.setTime(d.getDate());
			Integer dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
			d.setDateOfWeek(dayOfWeek);
			if (d.getShiftId() == 1) {
				listShift1.add(d);
			} else if (d.getShiftId() == 2) {
				listShift2.add(d);
			} else if (d.getShiftId() == 3) {
				listShift3.add(d);
			} else if (d.getShiftId() == 4) {
				listShift4.add(d);
			} else {
				listShift5.add(d);
			}
		}
		infor.setListShift1(listShift1);
		infor.setListShift2(listShift2);
		infor.setListShift3(listShift3);
		infor.setListShift4(listShift4);
		infor.setListShift5(listShift5);
		infor.setListDetail(listDetail);
		infor.setDateFrom(doctorScheduleSearchDto.getDateFrom());
		infor.setDateTo(doctorScheduleSearchDto.getDateTo());
		return infor;
	}
	
	@Override
	public List<Appointment> ListAllAppointment() {
		List<Appointment> listApp = (List<Appointment>) appointmentRepository.findAll();
		return listApp;
	}

	@Override
	public Appointment GetAppointmentByID(Long id) {
		Appointment app = appointmentRepository.findById(id).get();
		return app;
	}

	@Override
	public void SaveData(Appointment app) {
		appointmentRepository.save(app);
	}

	@Override
	public void Delete(Long id ) {
		appointmentRepository.deleteById(id);
	}

	@Override
	public List<Appointment> ListAllAppointmentByDoctorID(Long id) {
		List<Appointment> listApp = (List<Appointment>) appointmentRepository.GetAllAppByDoctorID(id);
		return listApp;
	}

	@Override
	public Integer GetPatientIDByAppID(Long id) {
		Integer PatientID = appointmentRepository.GetPatientIDByAppID(id);
		return PatientID;
	}
}
