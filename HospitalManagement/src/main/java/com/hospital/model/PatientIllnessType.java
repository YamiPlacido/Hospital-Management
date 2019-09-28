package com.hospital.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the patient_illness_type database table.
 * 
 */
@Entity
@Table(name="patient_illness_type")
@NamedQuery(name="PatientIllnessType.findAll", query="SELECT p FROM PatientIllnessType p")
public class PatientIllnessType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="patient_illness_type_id")
	private Integer patientIllnessTypeId;

	private String createdBy;

	private String modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

	//uni-directional many-to-one association to Patient
	@ManyToOne
	@JoinColumn(name="patient_id")
	private Patient patient;

	//uni-directional many-to-one association to IllnessType
	@ManyToOne
	@JoinColumn(name="illness_type_id")
	private IllnessType illnessType;

	public PatientIllnessType() {
	}

	public Integer getPatientIllnessTypeId() {
		return this.patientIllnessTypeId;
	}

	public void setPatientIllnessTypeId(Integer patientIllnessTypeId) {
		this.patientIllnessTypeId = patientIllnessTypeId;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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

	public Patient getPatient() {
		return this.patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public IllnessType getIllnessType() {
		return this.illnessType;
	}

	public void setIllnessType(IllnessType illnessType) {
		this.illnessType = illnessType;
	}

}