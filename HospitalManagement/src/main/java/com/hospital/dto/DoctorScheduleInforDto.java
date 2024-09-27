package com.hospital.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.hospital.model.Employee;


/**
 * The persistent class for the appointment database table.
 * 
 */
public class DoctorScheduleInforDto implements Serializable {


	private static final long serialVersionUID = -6883921139910900530L;


	private Employee employee;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateFrom;

	private List<DoctorScheduleDetailDto> listDetail;
	
	private List<DoctorScheduleDetailDto> listShift1;
	
	private List<DoctorScheduleDetailDto> listShift2;
	
	private List<DoctorScheduleDetailDto> listShift3;
	
	private List<DoctorScheduleDetailDto> listShift4;
	
	private List<DoctorScheduleDetailDto> listShift5;
	

	public List<DoctorScheduleDetailDto> getListShift1() {
		return listShift1;
	}

	public void setListShift1(List<DoctorScheduleDetailDto> listShift1) {
		this.listShift1 = listShift1;
	}

	public List<DoctorScheduleDetailDto> getListShift2() {
		return listShift2;
	}

	public void setListShift2(List<DoctorScheduleDetailDto> listShift2) {
		this.listShift2 = listShift2;
	}

	public List<DoctorScheduleDetailDto> getListShift3() {
		return listShift3;
	}

	public void setListShift3(List<DoctorScheduleDetailDto> listShift3) {
		this.listShift3 = listShift3;
	}

	public List<DoctorScheduleDetailDto> getListShift4() {
		return listShift4;
	}

	public void setListShift4(List<DoctorScheduleDetailDto> listShift4) {
		this.listShift4 = listShift4;
	}

	public List<DoctorScheduleDetailDto> getListShift5() {
		return listShift5;
	}

	public void setListShift5(List<DoctorScheduleDetailDto> listShift5) {
		this.listShift5 = listShift5;
	}

	public List<DoctorScheduleDetailDto> getListDetail() {
		return listDetail;
	}

	public void setListDetail(List<DoctorScheduleDetailDto> listDetail) {
		this.listDetail = listDetail;
	}

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}


}