package com.hospital.entity;

import com.hospital.model.Symptom;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "medical_care")
public class MedicalCare implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="medical_care_id")
	private int medicalCareId;

	@ManyToOne
	private Employee employee;
	@ManyToMany
	private List<Examination> examinations;
	@ManyToMany
	private List<Symptom> symptoms;

	private String conclusions;
	private String notes;
	private String status;
	
}
