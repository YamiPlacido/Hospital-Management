package com.hospital.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the examinator database table.
 * 
 */
@Entity
@NamedQuery(name="Examinator.findAll", query="SELECT e FROM Examinator e")
public class Examinator implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="examinator_id")
	private int examinatorId;

	private String name;

	//bi-directional many-to-one association to Examination
	@OneToMany(mappedBy="examinator")
	private List<Examination> examinations;

	public Examinator() {
	}

	public int getExaminatorId() {
		return this.examinatorId;
	}

	public void setExaminatorId(int examinatorId) {
		this.examinatorId = examinatorId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Examination> getExaminations() {
		return this.examinations;
	}

	public void setExaminations(List<Examination> examinations) {
		this.examinations = examinations;
	}

	public Examination addExamination(Examination examination) {
		getExaminations().add(examination);
		examination.setExaminator(this);

		return examination;
	}

	public Examination removeExamination(Examination examination) {
		getExaminations().remove(examination);
		examination.setExaminator(null);

		return examination;
	}

}