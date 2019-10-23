package com.hospital.dto;

import java.io.Serializable;
import java.util.List;

import com.hospital.model.Shift;


/**
 * The persistent class for the appointment database table.
 * 
 */
public class ShiftDto implements Serializable {

	private static final long serialVersionUID = -3034669421776725538L;

	private List<Shift> lstShift;

	public List<Shift> getLstShift() {
		return lstShift;
	}

	public void setLstShift(List<Shift> lstShift) {
		this.lstShift = lstShift;
	}

	

}