package com.hospital.model.dto;

import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the diagnosis database table.
 * 
 */
public class DiagnosiDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private int diagnosisId;
	
	private int appId;
	
	private String name;

	private String degree;

	private Date dianogsisTime;

	private Date createdDate;


	public DiagnosiDTO() {
	}
	
	
	public DiagnosiDTO(int diagnosisId, int appId, String name, String degree, Date dianogsisTime, Date createdDate) {
		super();
		this.diagnosisId = diagnosisId;
		this.appId = appId;
		this.name = name;
		this.degree = degree;
		this.dianogsisTime = dianogsisTime;
		this.createdDate = createdDate;
	}


	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public int getAppId() {
		return appId;
	}
	
	public void setAppId(int appId) {
		this.appId = appId;
	}
	
	public int getDiagnosisId() {
		return this.diagnosisId;
	}

	public void setDiagnosisId(int diagnosisId) {
		this.diagnosisId = diagnosisId;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getDegree() {
		return this.degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public Date getDianogsisTime() {
		return this.dianogsisTime;
	}

	public void setDianogsisTime(Date dianogsisTime) {
		this.dianogsisTime = dianogsisTime;
	}

}