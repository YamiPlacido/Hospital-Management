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

	@Column(name="created_by")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)

	@Column(name="created_date")
	private Date createdDate;

	@Temporal(TemporalType.DATE)
	@Column(name="followup_date")
	private Date followupDate;

	@Column(name="modified_by")
	private String modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modified_date")
	private Date modifiedDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="treatment_time")
	private Date treatmentTime;

	//uni-directional many-to-one association to Appointment
	@ManyToOne
	@JoinColumn(name="app_id")
	private Appointment appointment;

	//uni-directional many-to-one association to Illness
	@ManyToOne
	@JoinColumn(name="illness_id")
	private Illness illness;

	//uni-directional many-to-one association to TreatmentMethod
	@ManyToOne
	@JoinColumn(name="treatment_method_id")
	private TreatmentMethod treatmentMethod;

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

	public Illness getIllness() {
		return this.illness;
	}

	public void setIllness(Illness illness) {
		this.illness = illness;
	}

	public TreatmentMethod getTreatmentMethod() {
		return this.treatmentMethod;
	}

	public void setTreatmentMethod(TreatmentMethod treatmentMethod) {
		this.treatmentMethod = treatmentMethod;
	}

}