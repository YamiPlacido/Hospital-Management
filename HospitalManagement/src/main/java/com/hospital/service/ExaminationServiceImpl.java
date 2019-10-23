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
public class ExaminationServiceImpl implements ExaminationService {
   
 
	 @Autowired
	 ExaminationRepository ExaminatorRepository;
	 
	 @Override
	 public List<Examination> getAllExamination() {
		 List<Examination> invoicetList = (List<Examination>) ExaminatorRepository.findAll();
		 return invoicetList;
	 }

	 @Override
	 public Examination getExaminationById(long examinationId) {
	  return ExaminatorRepository.findById(examinationId).get();
	 }

	 @Override
	 public void saveOrUpdate(Examination examination) {
		 ExaminatorRepository.save(examination);
	 }

	 @Override
	 public void deleteExamination(long examinationId) {
		 ExaminatorRepository.deleteById(examinationId);
	 }

	}

