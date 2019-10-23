package com.hospital.repository;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.hospital.dto.AppointmentDto;
import com.hospital.dto.DoctorScheduleDetailDto;
import com.hospital.dto.DoctorScheduleInforDto;
import com.hospital.dto.DoctorScheduleSearchDto;
import com.hospital.model.Appointment;
import com.hospital.model.Shift;

import fr.opensagres.xdocreport.core.XDocReportException;

public interface AppointmentBookRepository{ 

	public List<Shift> getListShiftAvailabelByDoctorId(AppointmentDto appointmentDto);
	
	public int checkAppBeforeAdd(Long employee_id, Long shift_id, Date from, Date to);
	
	public Appointment saveApp(AppointmentDto appointmentDto);
	
	public List<AppointmentDto> getListAppointment();
	
	public void exportAppSlip(Long appId)throws IOException,XDocReportException;

	public List<DoctorScheduleDetailDto> getDoctorSchedule(DoctorScheduleSearchDto doctorScheduleSearchDto); 
}
