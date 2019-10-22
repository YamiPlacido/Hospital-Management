package com.hospital.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the appointment_examination database table.
 * 
 */
@Entity
@Table(name = "appointment_examination")
@NamedQuery(name = "AppointmentExamination.findAll", query = "SELECT a FROM AppointmentExamination a")
public class AppointmentExamination implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	@Column(name = "app_id")
	private int appointmentId;

	@Column(name = "ex_id")
	private int examinationId;

	public AppointmentExamination() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(int appointmentId) {
		this.appointmentId = appointmentId;
	}

	public int getExaminationId() {
		return examinationId;
	}
	
	public void setExaminationId(int examinationId) {
		this.examinationId = examinationId;
	}

}