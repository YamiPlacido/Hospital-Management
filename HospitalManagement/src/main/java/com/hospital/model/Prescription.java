package com.hospital.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the prescription database table.
 * 
 */
@Entity
@NamedQuery(name="Prescription.findAll", query="SELECT p FROM Prescription p")
public class Prescription implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="app_id")
	private int appId;

	private BigDecimal payment;

	//bi-directional one-to-one association to Appointment
	@OneToOne
	@JoinColumn(name="app_id")
	private Appointment appointment;

	public Prescription() {
	}

	public int getAppId() {
		return this.appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public BigDecimal getPayment() {
		return this.payment;
	}

	public void setPayment(BigDecimal payment) {
		this.payment = payment;
	}

	public Appointment getAppointment() {
		return this.appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}

}