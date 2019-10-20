package com.hospital.model;

import java.io.Serializable;
import javax.persistence.*;
;


/**
 * The persistent class for the speciality_illness_type database table.
 * 
 */
@Entity
@Table(name="speciality_illness_type")
@NamedQuery(name="SpecialityIllnessType.findAll", query="SELECT s FROM SpecialityIllnessType s")
public class SpecialityIllnessType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="speciality_illness_type_id")
	private String specialityIllnessTypeId;

	//bi-directional many-to-one association to IllnessType
	@ManyToOne
	@JoinColumn(name="illness_type_id")
	private IllnessType illnessType;

	//bi-directional many-to-one association to Speciality
	@ManyToOne
	@JoinColumn(name="speciality_id")
	private Speciality speciality;

	public SpecialityIllnessType() {
	}

	public String getSpecialityIllnessTypeId() {
		return this.specialityIllnessTypeId;
	}

	public void setSpecialityIllnessTypeId(String specialityIllnessTypeId) {
		this.specialityIllnessTypeId = specialityIllnessTypeId;
	}

	public IllnessType getIllnessType() {
		return this.illnessType;
	}

	public void setIllnessType(IllnessType illnessType) {
		this.illnessType = illnessType;
	}

	public Speciality getSpeciality() {
		return this.speciality;
	}

	public void setSpeciality(Speciality speciality) {
		this.speciality = speciality;
	}

}