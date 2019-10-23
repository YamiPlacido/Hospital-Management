package com.hospital.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "examination")
@NamedQuery(name="Examination.findAll", query="SELECT a FROM Examination a")
public class Examination implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="examination_id")
	private Long examinationId; 
	 
	private Double price; 
	
	private String name; 
	
	//bi-directional many-to-one association to Patient 

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	 

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Examination() {
	}

	public Long getExaminationId() {
		return examinationId;
	}

	public void setExaminationId(Long examinationId) {
		this.examinationId = examinationId;
	}
	
	
	
}