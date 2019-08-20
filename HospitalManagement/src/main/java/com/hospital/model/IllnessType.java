package com.hospital.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the illness_type database table.
 * 
 */
@Entity
@Table(name="illness_type")
@NamedQuery(name="IllnessType.findAll", query="SELECT i FROM IllnessType i")
public class IllnessType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="illness_type_id")
	private String illnessTypeId;

	private String name;

	private String note;

	//bi-directional many-to-one association to Illness
	@OneToMany(mappedBy="illnessType")
	private List<Illness> illnesses;

	//bi-directional many-to-one association to SpecialityIllnessType
	@OneToMany(mappedBy="illnessType")
	private List<SpecialityIllnessType> specialityIllnessTypes;

	public IllnessType() {
	}

	public String getIllnessTypeId() {
		return this.illnessTypeId;
	}

	public void setIllnessTypeId(String illnessTypeId) {
		this.illnessTypeId = illnessTypeId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public List<Illness> getIllnesses() {
		return this.illnesses;
	}

	public void setIllnesses(List<Illness> illnesses) {
		this.illnesses = illnesses;
	}

	public Illness addIllness(Illness illness) {
		getIllnesses().add(illness);
		illness.setIllnessType(this);

		return illness;
	}

	public Illness removeIllness(Illness illness) {
		getIllnesses().remove(illness);
		illness.setIllnessType(null);

		return illness;
	}

	public List<SpecialityIllnessType> getSpecialityIllnessTypes() {
		return this.specialityIllnessTypes;
	}

	public void setSpecialityIllnessTypes(List<SpecialityIllnessType> specialityIllnessTypes) {
		this.specialityIllnessTypes = specialityIllnessTypes;
	}

	public SpecialityIllnessType addSpecialityIllnessType(SpecialityIllnessType specialityIllnessType) {
		getSpecialityIllnessTypes().add(specialityIllnessType);
		specialityIllnessType.setIllnessType(this);

		return specialityIllnessType;
	}

	public SpecialityIllnessType removeSpecialityIllnessType(SpecialityIllnessType specialityIllnessType) {
		getSpecialityIllnessTypes().remove(specialityIllnessType);
		specialityIllnessType.setIllnessType(null);

		return specialityIllnessType;
	}

}