package com.hospital.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the illness_type database table.
 * 
 */
@Entity
@Table(name="illness_type")
@NamedQuery(name="IllnessType.findAll", query="SELECT i FROM IllnessType i")
public class IllnessType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="illness_type_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long illnessTypeId;

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

	private String name;

	private String note;

	public IllnessType() {
	}

	public Long getIllnessTypeId() {
		return this.illnessTypeId;
	}

	public void setIllnessTypeId(Long illnessTypeId) {
		this.illnessTypeId = illnessTypeId;
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