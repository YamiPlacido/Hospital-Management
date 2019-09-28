package com.hospital.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the symptom_type database table.
 * 
 */
@Entity
@Table(name="symptom_type")
@NamedQuery(name="SymptomType.findAll", query="SELECT s FROM SymptomType s")
public class SymptomType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="symptom_type_id")
	private Integer symptomTypeId;

	private String createdBy;

	private String modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

	private String name;

	private String note;

	public SymptomType() {
	}

	public Integer getSymptomTypeId() {
		return this.symptomTypeId;
	}

	public void setSymptomTypeId(Integer symptomTypeId) {
		this.symptomTypeId = symptomTypeId;
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}