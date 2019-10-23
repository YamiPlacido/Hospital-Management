package com.hospital.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;


/**
 * The persistent class for the appointment database table.
 * 
 */
public class AppointmentDto implements Serializable {

	private static final long serialVersionUID = -3034669421776725538L;

	private Long appId;

	private Long shiftId;
	
	private String patientName;

	private Long patientId;

	private String employeeName;
	
	private Long employeeId;
	
	private String note;
	
	private String message;
	
	private boolean status;
	
	public AppointmentDto(Long appId, String patientName, String employeeName, String note, boolean status, Date date) {
		super();
		this.appId = appId;
		this.patientName = patientName;
		this.employeeName = employeeName;
		this.note = note;
		this.status = status;
		this.date = date;
	}

	public AppointmentDto() {
		// TODO Auto-generated constructor stub
	}

	@Temporal(TemporalType.TIMESTAMP) 
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date date;

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public Long getShiftId() {
		return shiftId;
	}

	public void setShiftId(Long shiftId) {
		this.shiftId = shiftId;
	}

	public String getPatientName() {
		return patientName;
	}

	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


}