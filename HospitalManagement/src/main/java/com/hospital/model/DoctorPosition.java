package com.hospital.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the doctor_position database table.
 * 
 */
@Entity
@Table(name="doctor_position")
@NamedQuery(name="DoctorPosition.findAll", query="SELECT d FROM DoctorPosition d")
public class DoctorPosition implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="doctor_position_id")
	private int doctorPositionId;

	private String description;

	private byte disable;

	private String name;

	//bi-directional many-to-one association to Doctor
	@OneToMany(mappedBy="doctorPosition")
	private List<Doctor> doctors;

	public DoctorPosition() {
	}

	public int getDoctorPositionId() {
		return this.doctorPositionId;
	}

	public void setDoctorPositionId(int doctorPositionId) {
		this.doctorPositionId = doctorPositionId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte getDisable() {
		return this.disable;
	}

	public void setDisable(byte disable) {
		this.disable = disable;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Doctor> getDoctors() {
		return this.doctors;
	}

	public void setDoctors(List<Doctor> doctors) {
		this.doctors = doctors;
	}

	public Doctor addDoctor(Doctor doctor) {
		getDoctors().add(doctor);
		doctor.setDoctorPosition(this);

		return doctor;
	}

	public Doctor removeDoctor(Doctor doctor) {
		getDoctors().remove(doctor);
		doctor.setDoctorPosition(null);

		return doctor;
	}

}