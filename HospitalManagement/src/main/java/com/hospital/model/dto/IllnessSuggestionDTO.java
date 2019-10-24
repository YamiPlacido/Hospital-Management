package com.hospital.model.dto;

import java.io.Serializable;

import com.hospital.model.IllnessType;

public class IllnessSuggestionDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long illnessId;

	private String description;

	private String name;

	private String season;

	private String illnessTypeName;
	
	private Long couting;

	public Long getIllnessId() {
		return illnessId;
	}

	public void setIllnessId(Long illnessId) {
		this.illnessId = illnessId;
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

	public String getSeason() {
		return season;
	}

	public void setSeason(String season) {
		this.season = season;
	}

	public Long getCouting() {
		return couting;
	}

	public void setCouting(Long couting) {
		this.couting = couting;
	}
	
	public IllnessSuggestionDTO() {
		
	}

	public String getIllnessTypeName() {
		return illnessTypeName;
	}
	
	public void setIllnessTypeName(String illnessTypeName) {
		this.illnessTypeName = illnessTypeName;
	}

	public IllnessSuggestionDTO(Long illnessId, String description, String name, String season,
			String illnessTypeName, Long couting) {
		super();
		this.illnessId = illnessId;
		this.description = description;
		this.name = name;
		this.season = season;
		this.illnessTypeName = illnessTypeName;
		this.couting = couting;
	}
	
	
	
}
