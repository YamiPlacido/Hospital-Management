package com.hospital.model;

import com.hospital.entity.Appointment;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the diagnosis database table.
 * 
 */
@Entity
@Table(name="diagnosis")
@NamedQuery(name="Diagnosi.findAll", query="SELECT d FROM Diagnosi d")
public class Diagnosi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="diagnosis_id")
	private int diagnosisId;

	private String degree;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="dianogsis_time")
	private Date dianogsisTime;

	//bi-directional many-to-one association to Appointment
	@ManyToOne
	@JoinColumn(name="app_id")
	private Appointment appointment;

	//bi-directional many-to-one association to Illness
	@ManyToOne
	@JoinColumn(name="illness_id")
	private Illness illness;

	public Diagnosi() {
	}

	public int getDiagnosisId() {
		return this.diagnosisId;
	}

	public void setDiagnosisId(int diagnosisId) {
		this.diagnosisId = diagnosisId;
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

	public Appointment getAppointment() {
		return this.appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}

	public Illness getIllness() {
		return this.illness;
	}

	public void setIllness(Illness illness) {
		this.illness = illness;
	}

}