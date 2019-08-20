package com.hospital.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the examination database table.
 * 
 */
@Entity
@NamedQuery(name="Examination.findAll", query="SELECT e FROM Examination e")
public class Examination implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ex_id")
	private int exId;

	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	@Lob
	private String note;

	private byte status;

	//bi-directional many-to-one association to Appointment
	@ManyToOne
	@JoinColumn(name="app_id")
	private Appointment appointment;

	//bi-directional many-to-one association to Examinator
	@ManyToOne
	@JoinColumn(name="examinator_id")
	private Examinator examinator;

	//bi-directional many-to-one association to Patient
	@ManyToOne
	@JoinColumn(name="patient_id")
	private Patient patient;

	public Examination() {
	}

	public int getExId() {
		return this.exId;
	}

	public void setExId(int exId) {
		this.exId = exId;
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

	public byte getStatus() {
		return this.status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}

	public Appointment getAppointment() {
		return this.appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}

	public Examinator getExaminator() {
		return this.examinator;
	}

	public void setExaminator(Examinator examinator) {
		this.examinator = examinator;
	}

	public Patient getPatient() {
		return this.patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

}