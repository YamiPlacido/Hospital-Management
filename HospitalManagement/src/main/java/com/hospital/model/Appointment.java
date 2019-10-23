package com.hospital.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;


/**
 * The persistent class for the appointment database table.
 * 
 */
@Entity
@Table(name = "appointment")
@NamedQuery(name="Appointment.findAll", query="SELECT a FROM Appointment a")
public class Appointment implements Serializable {

	private static final long serialVersionUID = 5482207279862354571L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="app_id")
	private Long appId;

	@Temporal(TemporalType.TIMESTAMP) 
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="date")
	private Date date;
	
	@Lob
	@JoinColumn(name="note") 
	private String note;

	@JoinColumn(name="status") 
	private boolean status;
	 
	@JoinColumn(name="employee_id") 
	private Long employeeId;

	//bi-directional many-to-one association to Patient

	@JoinColumn(name="patient_id") 
	private Long patientId;
	
	@Temporal(TemporalType.TIMESTAMP) 
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "created_date")
	private Date createdDate;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Temporal(TemporalType.TIMESTAMP) 
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "modified_date")
	private Date modifiedDate;
	
	@Column(name = "modified_by")
	private String modifiedBy;

	@Column(name = "shift_id")
	private Long shiftId;
	
	@Column(name = "stage")
	private String stage;
	
	
	public String getStage() {
		return stage;
	}


	public void setStage(String stage) {
		this.stage = stage;
	}


	public Long getShiftId() {
		return shiftId;
	}


	public void setShiftId(Long shiftId) {
		this.shiftId = shiftId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}


	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}


	public String getModifiedBy() {
		return modifiedBy;
	}


	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}


	public Long getAppId() {
		return appId;
	}
 
	public void setAppId(Long appId) {
		this.appId = appId;
	}
 
	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}


	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

}