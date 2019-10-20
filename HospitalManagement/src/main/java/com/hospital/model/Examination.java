package com.hospital.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the examination database table.
 * 
 */
@Entity
//@NamedQuery(name="Examination.findAll", query="SELECT e FROM Examination e")
public class Examination implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ex_id")
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	@Lob
	private String note;
	private String content;
	private String result;
	private String stage;

	@Column(name="image_path_1")
	private String image_path_1;
	@Column(name="image_path_2")
	private String image_path_2;
	@Column(name="image_path_3")
	private String image_path_3;

	private String status;

	//bi-directional many-to-one association to Appointment
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="app_id")
	private Appointment appointment;

	@ManyToOne
	@JoinColumn(name="examination_type_id")
	private ExaminationType examinationType;

	//bi-directional many-to-one association to Examinator
//	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="examinator_id")
	private Employee examinator;

	//bi-directional many-to-one association to Patient
	@ManyToOne
	@JoinColumn(name="patient_id")
	private Patient patient;

	public Examination() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ExaminationType getExaminationType() {
		return examinationType;
	}

	public void setExaminationType(ExaminationType examinationType) {
		this.examinationType = examinationType;
	}

	public void setExaminator(Employee examinator) {
		this.examinator = examinator;
	}

	public Employee getExaminator() {
		return examinator;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Appointment getAppointment() {
		return this.appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}

	public Patient getPatient() {
		return this.patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getImage_path_1() {
		return image_path_1;
	}

	public void setImage_path_1(String image_path_1) {
		this.image_path_1 = image_path_1;
	}

	public String getImage_path_2() {
		return image_path_2;
	}

	public void setImage_path_2(String image_path_2) {
		this.image_path_2 = image_path_2;
	}

	public String getImage_path_3() {
		return image_path_3;
	}

	public void setImage_path_3(String image_path_3) {
		this.image_path_3 = image_path_3;
	}
}