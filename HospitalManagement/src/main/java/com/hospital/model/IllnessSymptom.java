package com.hospital.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the illness_symptom database table.
 * 
 */
@Entity
@Table(name="illness_symptom")
@NamedQuery(name="IllnessSymptom.findAll", query="SELECT i FROM IllnessSymptom i")
public class IllnessSymptom implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="illness_medicine_id")
	private int illnessMedicineId;

	//bi-directional many-to-one association to Illness
	@ManyToOne
	@JoinColumn(name="illness_id")
	private Illness illness;

	//bi-directional many-to-one association to Symptom
	@ManyToOne
	@JoinColumn(name="symptom_id")
	private Symptom symptom;

	public IllnessSymptom() {
	}

	public int getIllnessMedicineId() {
		return this.illnessMedicineId;
	}

	public void setIllnessMedicineId(int illnessMedicineId) {
		this.illnessMedicineId = illnessMedicineId;
	}

	public Illness getIllness() {
		return this.illness;
	}

	public void setIllness(Illness illness) {
		this.illness = illness;
	}

	public Symptom getSymptom() {
		return this.symptom;
	}

	public void setSymptom(Symptom symptom) {
		this.symptom = symptom;
	}

}