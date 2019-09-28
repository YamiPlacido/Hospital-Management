package com.hospital.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the medicine_illness database table.
 * 
 */
@Entity
@Table(name="medicine_illness")
@NamedQuery(name="MedicineIllness.findAll", query="SELECT m FROM MedicineIllness m")
public class MedicineIllness implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="medicine_illness_id")
	private int medicineIllnessId;

	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	private String modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;

	//uni-directional many-to-one association to Illness
	@ManyToOne
	@JoinColumn(name="illness_id")
	private Illness illness;

	//uni-directional many-to-one association to Medicine
	@ManyToOne
	@JoinColumn(name="medicine_id")
	private Medicine medicine;

	public MedicineIllness() {
	}

	public int getMedicineIllnessId() {
		return this.medicineIllnessId;
	}

	public void setMedicineIllnessId(int medicineIllnessId) {
		this.medicineIllnessId = medicineIllnessId;
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

	public Illness getIllness() {
		return this.illness;
	}

	public void setIllness(Illness illness) {
		this.illness = illness;
	}

	public Medicine getMedicine() {
		return this.medicine;
	}

	public void setMedicine(Medicine medicine) {
		this.medicine = medicine;
	}

}