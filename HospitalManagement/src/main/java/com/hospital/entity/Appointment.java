package com.hospital.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hospital.model.*;

/**
 * The persistent class for the appointment database table.
 * 
 */
@Entity
//@NamedQuery(name="Appointment.findAll", query="SELECT a FROM Appointment a")
public class Appointment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="app_id")
	private int appId;

	@Temporal(TemporalType.TIMESTAMP)
	private Date date;

	private String location;

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

	//bi-directional many-to-one association to Diagnosi
	@OneToMany(mappedBy="appointment")
	private List<Diagnosi> diagnosis;

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

	//bi-directional one-to-one association to Prescription
	@OneToOne(mappedBy="appointment")
	private Prescription prescription;

	//bi-directional many-to-one association to PrescriptionMedicine
	@OneToMany(mappedBy="appointment")
	private List<PrescriptionMedicine> prescriptionMedicines;

	public Appointment() {
	}

	public int getAppId() {
		return this.appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

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

	public List<Diagnosi> getDiagnosis() {
		return this.diagnosis;
	}

	public void setDiagnosis(List<Diagnosi> diagnosis) {
		this.diagnosis = diagnosis;
	}

	public List<Symptom> getSymptoms() {
		return symptoms;
	}

	public void setSymptoms(List<Symptom> symptoms) {
		this.symptoms = symptoms;
	}

	public List<Examination> getExaminations() {
		return examinations;
	}

	public void setExaminations(List<Examination> examinations) {
		this.examinations = examinations;
	}
//	public Diagnosi addDiagnosi(Diagnosi diagnosi) {
//		getDiagnosis().add(diagnosi);
//		diagnosi.setAppointment(this);
//
//		return diagnosi;
//	}
//
//	public Diagnosi removeDiagnosi(Diagnosi diagnosi) {
//		getDiagnosis().remove(diagnosi);
//		diagnosi.setAppointment(null);
//
//		return diagnosi;
//	}
//
//	public List<Examination> getExaminations() {
//		return this.examinations;
//	}
//
//	public void setExaminations(List<Examination> examinations) {
//		this.examinations = examinations;
//	}
//
//	public Examination addExamination(Examination examination) {
//		getExaminations().add(examination);
//		examination.setAppointment(this);
//
//		return examination;
//	}
//
//	public Examination removeExamination(Examination examination) {
//		getExaminations().remove(examination);
//		examination.setAppointment(null);
//
//		return examination;
//	}
//
//	public Prescription getPrescription() {
//		return this.prescription;
//	}
//
//	public void setPrescription(Prescription prescription) {
//		this.prescription = prescription;
//	}
//
//	public List<PrescriptionMedicine> getPrescriptionMedicines() {
//		return this.prescriptionMedicines;
//	}
//
//	public void setPrescriptionMedicines(List<PrescriptionMedicine> prescriptionMedicines) {
//		this.prescriptionMedicines = prescriptionMedicines;
//	}
//
//	public PrescriptionMedicine addPrescriptionMedicine(PrescriptionMedicine prescriptionMedicine) {
//		getPrescriptionMedicines().add(prescriptionMedicine);
//		prescriptionMedicine.setAppointment(this);
//
//		return prescriptionMedicine;
//	}
//
//	public PrescriptionMedicine removePrescriptionMedicine(PrescriptionMedicine prescriptionMedicine) {
//		getPrescriptionMedicines().remove(prescriptionMedicine);
//		prescriptionMedicine.setAppointment(null);
//
//		return prescriptionMedicine;
//	}

}