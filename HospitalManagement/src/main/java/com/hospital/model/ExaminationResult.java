package com.hospital.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the examination_result database table.
 * 
 */
@Entity
@Table(name="examination_result")
@NamedQuery(name="ExaminationResult.findAll", query="SELECT e FROM ExaminationResult e")
public class ExaminationResult implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="app_id")
	private int appId;

	private String detail;

	@Temporal(TemporalType.DATE)
	@Column(name="followup_date")
	private Date followupDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="result_time")
	private Date resultTime;

	@Column(name="treatment_method")
	private String treatmentMethod;

	public ExaminationResult() {
	}

	public int getAppId() {
		return this.appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public String getDetail() {
		return this.detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Date getFollowupDate() {
		return this.followupDate;
	}

	public void setFollowupDate(Date followupDate) {
		this.followupDate = followupDate;
	}

	public Date getResultTime() {
		return this.resultTime;
	}

	public void setResultTime(Date resultTime) {
		this.resultTime = resultTime;
	}

	public String getTreatmentMethod() {
		return this.treatmentMethod;
	}

	public void setTreatmentMethod(String treatmentMethod) {
		this.treatmentMethod = treatmentMethod;
	}

}