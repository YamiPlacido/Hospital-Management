package com.hospital.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the illness database table.
 * 
 */
@Entity
@NamedQuery(name="Illness.findAll", query="SELECT i FROM Illness i")
public class Illness implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="illness_id")
	private Integer illnessId;

	@Column(name="created_by")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_date")
	private Date createdDate;

	private String description;

	@Column(name="modified_by")
	private String modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modified_date")
	private Date modifiedDate;

	private String name;

	private String season;

	//uni-directional many-to-one association to IllnessType
	@ManyToOne
	@JoinColumn(name="illness_type_id")
	private IllnessType illnessType;

	public Illness() {
	}

	public Integer getIllnessId() {
		return this.illnessId;
	}

	public void setIllnessId(Integer illnessId) {
		this.illnessId = illnessId;
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

	public String getSeason() {
		return this.season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public IllnessType getIllnessType() {
		return this.illnessType;
	}

	public void setIllnessType(IllnessType illnessType) {
		this.illnessType = illnessType;
	}

}