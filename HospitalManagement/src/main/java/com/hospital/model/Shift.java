package com.hospital.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;


/**
 * The persistent class for the appointment database table.
 * 
 */
@Entity
@Table(name = "shift")
@NamedQuery(name="shift.findAll", query="SELECT a FROM Shift a")
public class Shift implements Serializable {

	private static final long serialVersionUID = 5207854116375888017L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="shift_id")
	private Long shiftId;

	@Temporal(TemporalType.TIMESTAMP) 
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="date")
	private Date  date;
	
	@Lob
	@JoinColumn(name="note") 
	private String note;

	@JoinColumn(name="status") 
	private boolean status;
	 
	@Column(name = "created_date")
	private String createdDate;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "modified_date")
	private String modifiedDate;
	
	@Column(name = "modified_by")
	private String modifiedBy;
	

	public Long getShiftId() {
		return shiftId;
	}


	public void setShiftId(Long shiftId) {
		this.shiftId = shiftId;
	}


	public String getCreatedDate() {
		return createdDate;
	}


	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}


	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	public String getModifiedDate() {
		return modifiedDate;
	}


	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}


	public String getModifiedBy() {
		return modifiedBy;
	}


	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}


	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	 
	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}
 
}