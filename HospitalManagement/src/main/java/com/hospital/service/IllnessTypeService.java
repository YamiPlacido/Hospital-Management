package com.hospital.service;

import java.util.List;

import com.hospital.model.IllnessType;

public interface IllnessTypeService {
	
	public List<IllnessType> ListAllIllnessType();
	
	public IllnessType GetIllnessTypeByID(long id);
	
	public void SaveData(IllnessType illnessType);
	
	public void Delete(long id);
}
