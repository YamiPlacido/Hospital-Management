package com.hospital.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import com.hospital.model.*;
import com.hospital.repository.*;

@Service
@Transactional
@Repository
@Component
public class ShiftServiceImpl implements ShiftService {
   
	 
	 @Autowired
	 ShiftRepository ShiftRepository;
	 
	 @Autowired
	 PatientRepository PatientRepository;
	 
	 


	 @Override
	 public List<Shift> getAllShift() {
		 List<Shift> shiftList = (List<Shift>) ShiftRepository.findAll();
		 return shiftList;
	 }
	 
	
	 @Override
	 public Shift getShiftById(long shiftId) {
	  return ShiftRepository.findById(shiftId).get();
	 }

	 @Override
	 public void saveOrUpdate(Shift shift) {
		 ShiftRepository.save(shift);
	 }

	 @Override
	 public void deleteShift(long shiftId) {
		 ShiftRepository.deleteById(shiftId);
	 }

	}

