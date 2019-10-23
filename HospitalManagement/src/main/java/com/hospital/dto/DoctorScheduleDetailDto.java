package com.hospital.dto;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;


/**
 * The persistent class for the appointment database table.
 * 
 */
public class DoctorScheduleDetailDto implements Serializable {

	private static final long serialVersionUID = 6171572215509021622L;

	private Long appId;
	
	private Long patientId;
	
	private String name;
	
	private String identityCard;
	
	private String phone;

	private Long shiftId;
	
	private boolean status;
	
	private Integer dateOfWeek;
	
	public Integer getDateOfWeek() {
		return dateOfWeek;
	}

	public void setDateOfWeek(Integer dateOfWeek) {
		this.dateOfWeek = dateOfWeek;
	}

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date date;
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

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

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdentityCard() {
		return identityCard;
	}

	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Long getShiftId() {
		return shiftId;
	}

	public void setShiftId(Long shiftId) {
		this.shiftId = shiftId;
	}
	
}