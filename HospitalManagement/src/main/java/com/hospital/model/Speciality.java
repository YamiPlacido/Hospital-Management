package com.hospital.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the speciality database table.
 * 
 */
@Entity
@NamedQuery(name="Speciality.findAll", query="SELECT s FROM Speciality s")
public class Speciality implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="speciality_id")
	private int specialityId;

	private String description;

	private String name;

	private String note;

	//bi-directional many-to-one association to Doctor
	@OneToMany(mappedBy="speciality")
	private List<Doctor> doctors;

	//bi-directional many-to-one association to SpecialityIllnessType
	@OneToMany(mappedBy="speciality")
	private List<SpecialityIllnessType> specialityIllnessTypes;

	public Speciality() {
	}

	public int getSpecialityId() {
		return this.specialityId;
	}

	public void setSpecialityId(int specialityId) {
		this.specialityId = specialityId;
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

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public List<Doctor> getDoctors() {
		return this.doctors;
	}

	public void setDoctors(List<Doctor> doctors) {
		this.doctors = doctors;
	}

	public Doctor addDoctor(Doctor doctor) {
		getDoctors().add(doctor);
		doctor.setSpeciality(this);

		return doctor;
	}

	public Doctor removeDoctor(Doctor doctor) {
		getDoctors().remove(doctor);
		doctor.setSpeciality(null);

		return doctor;
	}

	public List<SpecialityIllnessType> getSpecialityIllnessTypes() {
		return this.specialityIllnessTypes;
	}

	public void setSpecialityIllnessTypes(List<SpecialityIllnessType> specialityIllnessTypes) {
		this.specialityIllnessTypes = specialityIllnessTypes;
	}

	public SpecialityIllnessType addSpecialityIllnessType(SpecialityIllnessType specialityIllnessType) {
		getSpecialityIllnessTypes().add(specialityIllnessType);
		specialityIllnessType.setSpeciality(this);

		return specialityIllnessType;
	}

	public SpecialityIllnessType removeSpecialityIllnessType(SpecialityIllnessType specialityIllnessType) {
		getSpecialityIllnessTypes().remove(specialityIllnessType);
		specialityIllnessType.setSpeciality(null);

		return specialityIllnessType;
	}

}