package com.hospital.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the symptom database table.
 * 
 */
@Entity
@NamedQuery(name="Symptom.findAll", query="SELECT s FROM Symptom s")
public class Symptom implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="symptom_id")
	private String symptomId;

	private String degree;

	private String description;

	private String name;

	//bi-directional many-to-one association to IllnessSymptom
//	@OneToMany(fetch = FetchType.LAZY,mappedBy="symptom")
//	private List<IllnessSymptom> illnessSymptoms;

	public Symptom() {
	}

	public String getSymptomId() {
		return this.symptomId;
	}

	public void setSymptomId(String symptomId) {
		this.symptomId = symptomId;
	}

	public String getDegree() {
		return this.degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

//	public List<IllnessSymptom> getIllnessSymptoms() {
//		return this.illnessSymptoms;
//	}
//
//	public void setIllnessSymptoms(List<IllnessSymptom> illnessSymptoms) {
//		this.illnessSymptoms = illnessSymptoms;
//	}
//
//	public IllnessSymptom addIllnessSymptom(IllnessSymptom illnessSymptom) {
//		getIllnessSymptoms().add(illnessSymptom);
//		illnessSymptom.setSymptom(this);
//
//		return illnessSymptom;
//	}
//
//	public IllnessSymptom removeIllnessSymptom(IllnessSymptom illnessSymptom) {
//		getIllnessSymptoms().remove(illnessSymptom);
//		illnessSymptom.setSymptom(null);
//
//		return illnessSymptom;
//	}

}