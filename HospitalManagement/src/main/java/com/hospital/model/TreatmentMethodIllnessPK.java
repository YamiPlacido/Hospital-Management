package com.hospital.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the treatment_method_illness database table.
 * 
 */
@Embeddable
public class TreatmentMethodIllnessPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="treatment_method_illness_id")
	private int treatmentMethodIllnessId;

	@Column(name="treatment_method_id")
	private int treatmentMethodId;

	@Column(name="illness_id")
	private int illnessId;

	public TreatmentMethodIllnessPK() {
	}
	public int getTreatmentMethodIllnessId() {
		return this.treatmentMethodIllnessId;
	}
	public void setTreatmentMethodIllnessId(int treatmentMethodIllnessId) {
		this.treatmentMethodIllnessId = treatmentMethodIllnessId;
	}
	public int getTreatmentMethodId() {
		return this.treatmentMethodId;
	}
	public void setTreatmentMethodId(int treatmentMethodId) {
		this.treatmentMethodId = treatmentMethodId;
	}
	public int getIllnessId() {
		return this.illnessId;
	}
	public void setIllnessId(int illnessId) {
		this.illnessId = illnessId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TreatmentMethodIllnessPK)) {
			return false;
		}
		TreatmentMethodIllnessPK castOther = (TreatmentMethodIllnessPK)other;
		return 
			(this.treatmentMethodIllnessId == castOther.treatmentMethodIllnessId)
			&& (this.treatmentMethodId == castOther.treatmentMethodId)
			&& (this.illnessId == castOther.illnessId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.treatmentMethodIllnessId;
		hash = hash * prime + this.treatmentMethodId;
		hash = hash * prime + this.illnessId;
		
		return hash;
	}
}