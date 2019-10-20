package com.hospital.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the appointment database table.
 * 
 */
@Entity
//@NamedQuery(name="Appointment.findAll", query="SELECT a FROM Appointment a")
public class Appointment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="app_id")
	private int appId;

	@Column(name="created_by")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)

	@Column(name="created_date")
	private Date createdDate;

	@Temporal(TemporalType.DATE)
	private Date date;

	private String location;

	@Column(name="modified_by")
	private String modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modified_date")
	private Date modifiedDate;

	@Lob
	private String note;

	private byte status;

	//bi-directional many-to-one association to Doctor
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="employee_id")
	private Employee employee;

	//bi-directional many-to-one association to Patient
//	@JsonIgnore
	@ManyToOne()
	@JoinColumn(name="patient_id")
	private Patient patient;

	//bi-directional many-to-one association to Examination
	@ManyToMany
	@JoinTable(
			name = "appointment_examination",
			joinColumns = @JoinColumn(name = "app_id"),
			inverseJoinColumns = @JoinColumn(name = "ex_id"))
	private List<Examination> examinations;

	//bi-directional many-to-one association to Examination
//	@JsonIgnore
	@ManyToMany
	@JoinTable(
			name = "appointment_symptom",
			joinColumns = @JoinColumn(name = "app_id"),
			inverseJoinColumns = @JoinColumn(name = "symptom_id"))
	private List<Symptom> symptoms;

	private Time time;

	public Appointment() {
	}

	public int getAppId() {
		return this.appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public List<Symptom> getSymptoms() {
		return symptoms;
	}

	public void setSymptoms(List<Symptom> symptoms) {
		this.symptoms = symptoms;
	}

	public List<Examination> getExaminations() {
		return examinations;

	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	public byte getStatus() {
		return this.status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public Time getTime() {
		return this.time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public Employee getEmployee() {
		return this.employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Patient getPatient() {
		return this.patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

}