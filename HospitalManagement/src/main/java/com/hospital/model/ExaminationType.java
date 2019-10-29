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
	private long id;

	private String name;

	private String description;

	@JoinColumn(name = "speciality_id")
	private long specialityId;
//	private byte disable;

	@Transient
	boolean suggested = false;


	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public boolean isSuggested() {
		return suggested;
	}

	public void setSuggested(boolean suggested) {
		this.suggested = suggested;
	}
}
