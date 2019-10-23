package com.hospital.service;
import java.util.List;
 

import com.hospital.model.*;

public interface ExaminationService {  

	 public List<Examination> getAllExamination(); 
	 
	 public Examination getExaminationById(long examinationId);
	 
	 public void saveOrUpdate(Examination examination);
	 
	 public void deleteExamination(long examinationId);

	
	
}
