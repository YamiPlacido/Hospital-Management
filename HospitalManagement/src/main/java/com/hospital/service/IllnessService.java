package com.hospital.service;

import java.util.List;

import com.hospital.model.Illness;
import com.hospital.model.IllnessType;

public interface IllnessService {
	
	public List<Illness> ListAllIllness();
	
	public Illness GetIllnessByID(Long id);
	
	public void SaveData(Illness illnesss);
	
	public void Delete(Long id);
	
	public List<IllnessType> ListAllIllnessType();
}
