package com.hospital.service;
import java.util.List;
 

import com.hospital.model.*;

public interface ShiftService {  

	 public List<Shift> getAllShift();
	 
	 public Shift getShiftById(long shiftId);
	 
	 public void saveOrUpdate(Shift shift);
	 
	 public void deleteShift(long shiftId);

	
	
}
