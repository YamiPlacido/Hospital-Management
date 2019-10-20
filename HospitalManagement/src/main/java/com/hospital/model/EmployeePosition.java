package com.hospital.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the employee_position database table.
 * 
 */
@Entity
@Table(name="employee_position")
@NamedQuery(name="EmployeePosition.findAll", query="SELECT e FROM EmployeePosition e")
public class EmployeePosition implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="employee_position_id")
	private int employeePositionId;

	@Column(name="created_by")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_date")
	private Date createdDate;

	private String description;

	private byte disable;

	@Column(name="modified_by")
	private String modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modified_date")
	private Date modifiedDate;

	private String name;

	public EmployeePosition() {
	}

	public int getEmployeePositionId() {
		return this.employeePositionId;
	}

	public void setEmployeePositionId(int employeePositionId) {
		this.employeePositionId = employeePositionId;
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

	public byte getDisable() {
		return this.disable;
	}

	public void setDisable(byte disable) {
		this.disable = disable;
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

}