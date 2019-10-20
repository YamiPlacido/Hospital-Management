package com.hospital.model;

import com.hospital.entity.Appointment;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;


/**
 * The persistent class for the prescription_medicine database table.
 * 
 */
@Entity
@Table(name="prescription_medicine")
@NamedQuery(name="PrescriptionMedicine.findAll", query="SELECT p FROM PrescriptionMedicine p")
public class PrescriptionMedicine implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="pm_id")
	private int pmId;

	private String dosage;

	private BigDecimal price;

	private BigInteger quantity;

	//bi-directional many-to-one association to Appointment
	@ManyToOne
	@JoinColumn(name="app_id")
	private Appointment appointment;

	//bi-directional many-to-one association to Medicine
	@ManyToOne
	@JoinColumn(name="medicine_id")
	private Medicine medicine;

	public PrescriptionMedicine() {
	}

	public int getPmId() {
		return this.pmId;
	}

	public void setPmId(int pmId) {
		this.pmId = pmId;
	}

	public String getDosage() {
		return this.dosage;
	}

	public void setDosage(String dosage) {
		this.dosage = dosage;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigInteger getQuantity() {
		return this.quantity;
	}

	public void setQuantity(BigInteger quantity) {
		this.quantity = quantity;
	}

	public Appointment getAppointment() {
		return this.appointment;
	}

	public void setAppointment(Appointment appointment) {
		this.appointment = appointment;
	}

	public Medicine getMedicine() {
		return this.medicine;
	}

	public void setMedicine(Medicine medicine) {
		this.medicine = medicine;
	}

}