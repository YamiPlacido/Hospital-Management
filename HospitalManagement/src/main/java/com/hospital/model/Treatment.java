package com.hospital.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the treatment database table.
 * 
 */
@Entity
@NamedQuery(name="Treatment.findAll", query="SELECT t FROM Treatment t")
public class Treatment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="treatment_id")
	private int treatmentId;

	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	@Temporal(TemporalType.DATE)
	@Column(name="followup_date")
	private Date followupDate;

	private String modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

	@Column(name="treatment_method_illness_id")
	private int treatmentMethodIllnessId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="treatment_time")
	private Date treatmentTime;

	//uni-directional many-to-one association to Appointment
	@ManyToOne
	@JoinColumn(name="app_id")
	private Appointment appointment;

	//uni-directional many-to-one association to Employee
	@ManyToOne
	@JoinColumn(name="employee_id")
	private Employee employee;

	//uni-directional many-to-one association to Patient
	@ManyToOne
	@JoinColumn(name="patient_id")
	private Patient patient;

	public Treatment() {
	}

	public int getTreatmentId() {
		return this.treatmentId;
	}

	public void setTreatmentId(int treatmentId) {
		this.treatmentId = treatmentId;
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

	public Date getFollowupDate() {
		return this.followupDate;
	}

	public void setFollowupDate(Date followupDate) {
		this.followupDate = followupDate;
	}

	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public int getTreatmentMethodIllnessId() {
		return this.treatmentMethodIllnessId;
	}

	public void setTreatmentMethodIllnessId(int treatmentMethodIllnessId) {
		this.treatmentMethodIllnessId = treatmentMethodIllnessId;
	}

	public Date getTreatmentTime() {
		return this.treatmentTime;
	}

	public void setTreatmentTime(Date treatmentTime) {
		this.treatmentTime = treatmentTime;
	}

	public Appointment getAppointment() {
		return this.appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
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