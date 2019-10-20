package com.hospital.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "examination_type")
public class ExaminationType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	private String name;

	private String description;

	@ManyToOne
	@JoinColumn(name = "speciality_id")
	private Speciality speciality;
//	private byte disable;



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
