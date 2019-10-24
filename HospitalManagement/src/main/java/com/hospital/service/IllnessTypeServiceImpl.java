package com.hospital.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hospital.model.IllnessType;
import com.hospital.repository.IllnessTypeRepository;

@Component
public class IllnessTypeServiceImpl implements IllnessTypeService {

	@Autowired
	IllnessTypeRepository illnessTypeRepo;

	@Override
	public List<IllnessType> ListAllIllnessType() {
		List<IllnessType> listIllnessType = (List<IllnessType>) illnessTypeRepo.findAll();
		return listIllnessType;
	}

	@Override
	public IllnessType GetIllnessTypeByID(long id) {
		IllnessType illnessType = illnessTypeRepo.findById(id).get();
		return illnessType;
	}

	@Override
	public void SaveData(IllnessType illnessType) {
		illnessTypeRepo.save(illnessType);		
	}

	@Override
	public void Delete(long id) {
		illnessTypeRepo.deleteById(id);
	}

}
