package com.hospital.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.math.BigInteger;


/**
 * The persistent class for the symptom database table.
 * 
 */
@Entity
@NamedQuery(name="Symptom.findAll", query="SELECT s FROM Symptom s")
public class Symptom implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="symptom_id")
	private Integer symptomId;

	private String createdBy;

	private String degree;

	private String description;

	private String modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

	private String name;

	@Column(name="symptom_type_id")
	private BigInteger symptomTypeId;

	//uni-directional many-to-one association to Examination
	@ManyToOne
	@JoinColumn(name="ex_id")
	private Examination examination;

	public Symptom() {
	}

	public Integer getSymptomId() {
		return this.symptomId;
	}

	public void setSymptomId(Integer symptomId) {
		this.symptomId = symptomId;
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

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigInteger getSymptomTypeId() {
		return this.symptomTypeId;
	}

	public void setSymptomTypeId(BigInteger symptomTypeId) {
		this.symptomTypeId = symptomTypeId;
	}

	public Examination getExamination() {
		return this.examination;
	}

	public void setExamination(Examination examination) {
		this.examination = examination;
	}

}