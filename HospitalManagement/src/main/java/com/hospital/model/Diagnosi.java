package com.hospital.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the diagnosis database table.
 * 
 */
@Entity
@Table(name="diagnosis")
@NamedQuery(name="Diagnosi.findAll", query="SELECT d FROM Diagnosi d")
public class Diagnosi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="diagnosis_id")
	private int diagnosisId;

	private String createdBy;

	private String degree;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dianogsis_time")
	private Date dianogsisTime;

	private String modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

	//uni-directional many-to-one association to Illness
	@ManyToOne
	@JoinColumn(name="illness_id")
	private Illness illness;

	//uni-directional many-to-one association to Appointment
	@ManyToOne
	@JoinColumn(name="app_id")
	private Appointment appointment;

	public Diagnosi() {
	}

	public int getDiagnosisId() {
		return this.diagnosisId;
	}

	public void setDiagnosisId(int diagnosisId) {
		this.diagnosisId = diagnosisId;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getDegree() {
		return this.degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public Date getDianogsisTime() {
		return this.dianogsisTime;
	}

	public void setDianogsisTime(Date dianogsisTime) {
		this.dianogsisTime = dianogsisTime;
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

	public Illness getIllness() {
		return this.illness;
	}

	public void setIllness(Illness illness) {
		this.illness = illness;
	}

	public Appointment getAppointment() {
		return this.appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}

}