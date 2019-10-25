package com.hospital.model;

import java.io.Serializable;

import javax.persistence.*;

@Entity
public class Speciality implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="speciality_id")
	private Long specialityId;

	private String name;

	private String description;
	@ManyToOne
	@JoinColumn(name="position_id")
	private Position position;

//	@OneToMany
//	private ExaminationType examinationType;

	public Long getSpecialityId() {
		return specialityId;
	}

	public void setSpecialityId(Long specialityId) {
		this.specialityId = specialityId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Position getPosition() {
		return position;
	}
	public void setPosition(Position position) {
		this.position = position;
	}
}
