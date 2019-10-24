package com.hospital.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the treatment_method_illness database table.
 * 
 */
@Entity
@Table(name="treatment_method_illness")
@NamedQuery(name="TreatmentMethodIllness.findAll", query="SELECT t FROM TreatmentMethodIllness t")
public class TreatmentMethodIllness implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="treatment_method_illness_id")
	private Long treatmentMethodIllnessId;

	@Column(name="created_by")
	private String createdBy;

	@Temporal(TemporalType.TIMESTAMP)

	@Column(name="created_date")
	private Date createdDate;

	@Column(name="modified_by")
	private String modifiedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="modified_date")
	private Date modifiedDate;

	//uni-directional many-to-one association to Illness
	@ManyToOne
	@JoinColumn(name="illness_id")
	private Illness illness;

	//uni-directional many-to-one association to TreatmentMethod
	@ManyToOne
	@JoinColumn(name="treatment_method_id")
	private TreatmentMethod treatmentMethod;

	public TreatmentMethodIllness() {
	}

	public Long getTreatmentMethodIllnessId() {
		return this.treatmentMethodIllnessId;
	}

	public void setTreatmentMethodIllnessId(Long treatmentMethodIllnessId) {
		this.treatmentMethodIllnessId = treatmentMethodIllnessId;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Illness getIllness() {
		return this.illness;
	}

	public void setIllness(Illness illness) {
		this.illness = illness;
	}

	public TreatmentMethod getTreatmentMethod() {
		return this.treatmentMethod;
	}

	public void setTreatmentMethod(TreatmentMethod treatmentMethod) {
		this.treatmentMethod = treatmentMethod;
	}

}