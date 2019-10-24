package com.hospital.model.dto;

import java.io.Serializable;
import java.util.Date;

public class TreatmentSuggestionDTO implements Serializable {
	
	public TreatmentSuggestionDTO(Long treatmentMethodId, String detail, String name,Long couting) {
		super();
		this.treatmentMethodId = treatmentMethodId;
		this.detail = detail;
		this.name = name;
		this.couting = couting;
	}

	private static final long serialVersionUID = 1L;

	private Long treatmentMethodId;

	private String createdBy;

	private Date createdDate;

	private String detail;

	private String modifiedBy;

	private Date modifiedDate;

	private String name;
	
	private Long couting;
	
	public Long getCouting() {
		return couting;
	}
	public void setCouting(Long couting) {
		this.couting = couting;
	}

	public TreatmentSuggestionDTO() {
	}

	public Long getTreatmentMethodId() {
		return this.treatmentMethodId;
	}

	public void setTreatmentMethodId(Long treatmentMethodId) {
		this.treatmentMethodId = treatmentMethodId;
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

	public String getDetail() {
		return this.detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
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