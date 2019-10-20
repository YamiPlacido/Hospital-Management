package com.hospital.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the patient database table.
 * 
 */
@Entity
//@NamedQuery(name="Patient.findAll", query="SELECT p FROM Patient p")
public class Patient implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="patient_id")
	private int patientId;

	private String address;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dob;

	private String email;

	@Lob
	@Column(name="image_url")
	private byte[] imageUrl;

	private String name;

	@Lob
	@Column(name="patient_note")
	private String patientNote;

	private String phone;

	//bi-directional many-to-one association to Appointment
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY,mappedBy="patient")
	private List<Appointment> appointments;

	//bi-directional many-to-one association to Examination
	@JsonIgnore
	@OneToMany //(mappedBy="patient")
	private List<Examination> examinations;

	public Patient() {
	}

	public int getPatientId() {
		return this.patientId;
	}

	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getDob() {
		return this.dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public byte[] getImageUrl() {
		return this.imageUrl;
	}

	public void setImageUrl(byte[] imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPatientNote() {
		return this.patientNote;
	}

	public void setPatientNote(String patientNote) {
		this.patientNote = patientNote;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public List<Appointment> getAppointments() {
		return this.appointments;
	}

	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}

	public Appointment addAppointment(Appointment appointment) {
		getAppointments().add(appointment);
		appointment.setPatient(this);

		return appointment;
	}

	public Appointment removeAppointment(Appointment appointment) {
		getAppointments().remove(appointment);
		appointment.setPatient(null);

		return appointment;
	}

	public List<Examination> getExaminations() {
		return this.examinations;
	}

	public void setExaminations(List<Examination> examinations) {
		this.examinations = examinations;
	}

	public Examination addExamination(Examination examination) {
		getExaminations().add(examination);
		examination.setPatient(this);

		return examination;
	}

	public Examination removeExamination(Examination examination) {
		getExaminations().remove(examination);
		examination.setPatient(null);

		return examination;
	}

}