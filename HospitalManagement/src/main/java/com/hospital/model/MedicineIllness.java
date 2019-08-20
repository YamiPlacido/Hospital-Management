package com.hospital.model;

import java.io.Serializable;
import javax.persistence.*;


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
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="medicine_illness_id")
	private int medicineIllnessId;

	//bi-directional many-to-one association to Illness
	@ManyToOne
	@JoinColumn(name="illness_id")
	private Illness illness;

	//bi-directional many-to-one association to Medicine
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