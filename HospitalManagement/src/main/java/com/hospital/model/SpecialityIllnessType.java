package com.hospital.model;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the speciality_illness_type database table.
 * 
 */
@Entity
@Table(name="speciality_illness_type")
@NamedQuery(name="SpecialityIllnessType.findAll", query="SELECT s FROM SpecialityIllnessType s")
public class SpecialityIllnessType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="speciality_illness_type_id")
	private Integer specialityIllnessTypeId;

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

	//uni-directional many-to-one association to IllnessType
	@ManyToOne
	@JoinColumn(name="illness_type_id")
	private IllnessType illnessType;

	//uni-directional many-to-one association to Speciality
	@ManyToOne
	@JoinColumn(name="speciality_id")
	private Speciality speciality;

	public SpecialityIllnessType() {
	}

	public Integer getSpecialityIllnessTypeId() {
		return this.specialityIllnessTypeId;
	}

	public void setSpecialityIllnessTypeId(Integer specialityIllnessTypeId) {
		this.specialityIllnessTypeId = specialityIllnessTypeId;
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

	public IllnessType getIllnessType() {
		return this.illnessType;
	}

	public void setIllnessType(IllnessType illnessType) {
		this.illnessType = illnessType;
	}

	public Speciality getSpeciality() {
		return this.speciality;
	}

	public void setSpeciality(Speciality speciality) {
		this.speciality = speciality;
	}

}