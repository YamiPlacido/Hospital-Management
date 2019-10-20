package com.hospital.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hospital.model.Illness;
import com.hospital.model.IllnessType;
import com.hospital.repository.IllnessRepository;
import com.hospital.repository.IllnessTypeRepository;

@Component
public class IllnessServiceImpl implements IllnessService {

	@Autowired
	IllnessRepository illnessRepo;
	
	@Autowired
	IllnessTypeRepository illnessTypeRepo;

	@Override
	public List<Illness> ListAllIllness() {
		List<Illness> listIllness = (List<Illness>) illnessRepo.findAll();
		return listIllness;
	}

	@Override
	public Illness GetIllnessByID(Integer id) {
		Illness illness = illnessRepo.findById(id).get();
		return illness;
	}

	@Override
	public void SaveData(Illness illness) {
		illnessRepo.save(illness);
	}

	@Override
	public void Delete(Integer id) {
		illnessRepo.deleteById(id);
	}

	@Override
	public List<IllnessType> ListAllIllnessType() {
		List<IllnessType> illnessType = (List<IllnessType>) illnessTypeRepo.findAll();
		return illnessType;
	}

}
