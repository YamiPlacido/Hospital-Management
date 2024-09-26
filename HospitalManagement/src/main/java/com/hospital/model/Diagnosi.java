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

	@Column(name="created_by")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)

	@Column(name="created_date")
  
	private Date createdDate;

	private String degree;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dianogsis_time")
	private Date dianogsisTime;

	@Column(name="modified_by")
	private String modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modified_date")
	private Date modifiedDate;

	//uni-directional many-to-one association to Appointment
	@ManyToOne
	@JoinColumn(name="app_id")
	private Appointment appointment;

	//uni-directional many-to-one association to Illness
	@ManyToOne
	@JoinColumn(name="illness_id")
	private Illness illness;

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

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
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

	public Appointment getAppointment() {
		return this.appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}

	public Illness getIllness() {
		return this.illness;
	}

	public void setIllness(Illness illness) {
		this.illness = illness;
	}

}