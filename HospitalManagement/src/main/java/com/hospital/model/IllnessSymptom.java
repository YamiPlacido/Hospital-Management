package com.hospital.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the illness_symptom database table.
 * 
 */
@Entity
@Table(name="illness_symptom")
@NamedQuery(name="IllnessSymptom.findAll", query="SELECT i FROM IllnessSymptom i")
public class IllnessSymptom implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="illness_symptom_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long illnessSymptomId;

	@Column(name="created_by")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)

	@Column(name="created_date")
	private Date createdDate;

	@Column(name="modified_by")

	private String modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modified_date")
	private Date modifiedDate;

	//uni-directional many-to-one association to Illness
	@ManyToOne
	@JoinColumn(name="illness_id")
	private Illness illness;

	//uni-directional many-to-one association to Symptom
	@ManyToOne
	@JoinColumn(name="symptom_id")
	private Symptom symptom;

	public IllnessSymptom() {
	}

	public Long getIllnessSymptomId() {
		return this.illnessSymptomId;
	}

	public void setIllnessSymptomId(Long illnessSymptomId) {
		this.illnessSymptomId = illnessSymptomId;
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

	public Symptom getSymptom() {
		return this.symptom;
	}

	public void setSymptom(Symptom symptom) {
		this.symptom = symptom;
	}

}