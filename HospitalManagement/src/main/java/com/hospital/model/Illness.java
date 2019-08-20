package com.hospital.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the illness database table.
 * 
 */
@Entity
@NamedQuery(name="Illness.findAll", query="SELECT i FROM Illness i")
public class Illness implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="illness_id")
	private String illnessId;

	private String description;

	private String name;

	private String season;

	//bi-directional many-to-one association to Diagnosi
	@OneToMany(mappedBy="illness")
	private List<Diagnosi> diagnosis;

	//bi-directional many-to-one association to IllnessType
	@ManyToOne
	@JoinColumn(name="illness_type_id")
	private IllnessType illnessType;

	//bi-directional many-to-one association to IllnessSymptom
	@OneToMany(mappedBy="illness")
	private List<IllnessSymptom> illnessSymptoms;

	//bi-directional many-to-one association to MedicineIllness
	@OneToMany(mappedBy="illness")
	private List<MedicineIllness> medicineIllnesses;

	public Illness() {
	}

	public String getIllnessId() {
		return this.illnessId;
	}

	public void setIllnessId(String illnessId) {
		this.illnessId = illnessId;
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

	public String getSeason() {
		return this.season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public List<Diagnosi> getDiagnosis() {
		return this.diagnosis;
	}

	public void setDiagnosis(List<Diagnosi> diagnosis) {
		this.diagnosis = diagnosis;
	}

	public Diagnosi addDiagnosi(Diagnosi diagnosi) {
		getDiagnosis().add(diagnosi);
		diagnosi.setIllness(this);

		return diagnosi;
	}

	public Diagnosi removeDiagnosi(Diagnosi diagnosi) {
		getDiagnosis().remove(diagnosi);
		diagnosi.setIllness(null);

		return diagnosi;
	}

	public IllnessType getIllnessType() {
		return this.illnessType;
	}

	public void setIllnessType(IllnessType illnessType) {
		this.illnessType = illnessType;
	}

	public List<IllnessSymptom> getIllnessSymptoms() {
		return this.illnessSymptoms;
	}

	public void setIllnessSymptoms(List<IllnessSymptom> illnessSymptoms) {
		this.illnessSymptoms = illnessSymptoms;
	}

	public IllnessSymptom addIllnessSymptom(IllnessSymptom illnessSymptom) {
		getIllnessSymptoms().add(illnessSymptom);
		illnessSymptom.setIllness(this);

		return illnessSymptom;
	}

	public IllnessSymptom removeIllnessSymptom(IllnessSymptom illnessSymptom) {
		getIllnessSymptoms().remove(illnessSymptom);
		illnessSymptom.setIllness(null);

		return illnessSymptom;
	}

	public List<MedicineIllness> getMedicineIllnesses() {
		return this.medicineIllnesses;
	}

	public void setMedicineIllnesses(List<MedicineIllness> medicineIllnesses) {
		this.medicineIllnesses = medicineIllnesses;
	}

	public MedicineIllness addMedicineIllness(MedicineIllness medicineIllness) {
		getMedicineIllnesses().add(medicineIllness);
		medicineIllness.setIllness(this);

		return medicineIllness;
	}

	public MedicineIllness removeMedicineIllness(MedicineIllness medicineIllness) {
		getMedicineIllnesses().remove(medicineIllness);
		medicineIllness.setIllness(null);

		return medicineIllness;
	}

}