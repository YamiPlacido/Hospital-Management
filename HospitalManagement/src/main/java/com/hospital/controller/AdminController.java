package com.hospital.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.hospital.model.Employee;
import com.hospital.model.Position;
import com.hospital.model.Speciality;
import com.hospital.repository.EmployeeRepository;
import com.hospital.repository.PositionRepository;
import com.hospital.repository.SpecialityRepository;
import com.hospital.service.HelperService;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	SpecialityRepository specialityRepository;
	@Autowired
	PositionRepository positionRepository;
	@Autowired
	HelperService helperService;
	
	private static String UPLOADED_FOLDER = "upload/";
	boolean edited = false;
	
	@GetMapping("doctor")
	public ModelAndView createFirstView() {
		ModelAndView mav = new ModelAndView("admin/doctor-view");
		return mav;
	}
	
	@GetMapping("doctor/{id}")
	public ModelAndView viewDetail(@PathVariable("id") Long id) {
        ModelAndView mav = new ModelAndView("admin/doctor-detail-view");
        Employee employee = employeeRepository.findById(id).get();
        Long specialityId = employee.getSpecialityId();
        Long positionId = employee.getPositionId();
        Speciality speciality = specialityRepository.findById(specialityId).get();
        Position position = positionRepository.findById(positionId).get();
		employee.setSpeciality(speciality);
		employee.setPosition(position);
        mav.addObject("employee",employee);
        return mav;
    }
	
	@GetMapping("employee/create")
	public ModelAndView doctorCreateForm() {
		edited = false;
		Employee employee = new Employee();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, -18);
		Date dateBefore18Years = cal.getTime();
		employee.setDob(dateBefore18Years);
		employee.setStatus("1");
		//bo sung do thieu mapping
//		Long specialityId = employee.getSpecialityId();
//		Long positionId = employee.getPositionId();
//		Speciality speciality = specialityRepository.findById(specialityId).get();
//		Position position = positionRepository.findById(positionId).get();
//		employee.setSpeciality(speciality);
//		employee.setPosition(position);
		
		List<Speciality> specialities =  specialityRepository.findByPositionId((long) 1);
		List<Position> positions =  positionRepository.findAll();

		ModelAndView mav = new ModelAndView("admin/employee-create");
		mav.addObject("employee", employee);
		mav.addObject("positions", positions);
        mav.addObject("specialities", specialities);
        mav.addObject("edited",edited);
		return mav;
	}
	
	@GetMapping("employee/edit/{id}")
	public ModelAndView editDoctor(@PathVariable("id") Long id) {
		edited = true;
		List<Speciality> specialities =  specialityRepository.findAll();
		List<Position> positions =  positionRepository.findAll();
		ModelAndView mav = new ModelAndView("admin/employee-create");
		mav.addObject("positions", positions);
        mav.addObject("specialities", specialities);
        mav.addObject("employee", employeeRepository.findById(id).get());
        mav.addObject("edited",edited);
        return mav;
    }
	
	@PostMapping("/employee/create")
    public String checkPersonInfoWhenCreate(
    		@Valid @ModelAttribute(value="employee") Employee employee,
    		@RequestParam("dob") String dob,
    		@RequestParam("file") MultipartFile file,
    		BindingResult bindingResult) {
		
        if (bindingResult.hasErrors()) {
            return "admin/employee-create";
        } else {
        	employee.setImagePath(file.getOriginalFilename());
//        	doctor.setPosition(positionRepository.getOne(1));
        	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        	Calendar cal = Calendar.getInstance();
        	try {
        		Date parsedDate = formatter.parse(dob);
        		cal.setTime(parsedDate);
        		cal.add(Calendar.DATE, 1);
        		parsedDate = cal.getTime();
        		employee.setDob(parsedDate);
        	} catch (ParseException e) {
        		// TODO Auto-generated catch block
        		e.printStackTrace();
        	}
        	if (file.isEmpty()) {
              return "redirect:error.html";
        	}
  
          try {
              // Get the file and save it somewhere
              byte[] bytes = file.getBytes();
              Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
              Files.write(path, bytes);
          } catch (IOException e) {
              e.printStackTrace();
          }
        
        	employee.setCode(helperService.createRandomCodeNumber(8));
//        	employee.setPassword(randomService.createRandomPassword());
        	employeeRepository.save(employee);
        	return "redirect:/admin/doctor";
        }

	}
	
	@PostMapping("/employee/edit")
    public String checkPersonInfoWhenEdit(
    		@Valid @ModelAttribute(value="employee") Employee employee,
    		@RequestParam("dob") String dob,
    		@RequestParam("file") MultipartFile file,
    		BindingResult bindingResult) {
		
        if (bindingResult.hasErrors()) {
            return "admin/employee-create";
        } else {
        	Employee oldEmployee = employeeRepository.findById(employee.getEmployeeId()).get();
    		employee.setCode(oldEmployee.getCode());
//        	employee.setPassword(oldEmployee.getPassword());
        	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        	Calendar cal = Calendar.getInstance();
        	try {
        		Date parsedDate = formatter.parse(dob);
        		cal.setTime(parsedDate);
        		cal.add(Calendar.DATE, 1);
        		parsedDate = cal.getTime();
        		employee.setDob(parsedDate);
        	} catch (ParseException e) {
        		// TODO Auto-generated catch block
        		e.printStackTrace();
        	}
        	if (file.isEmpty()) {
        		employee.setImagePath(oldEmployee.getImagePath());
        	} else {
        		employee.setImagePath(file.getOriginalFilename());
        		try {
        			// Get the file and save it somewhere
        			byte[] bytes = file.getBytes();
        			Path path = Paths.get(UPLOADED_FOLDER +"employee/"+ file.getOriginalFilename());
        			Files.write(path, bytes);
        		} catch (IOException e) {
        			e.printStackTrace();
        		}
        	}
        	employeeRepository.save(employee);
        	return "redirect:admin/admin/doctor";
        }

	}

	@ResponseBody
	@GetMapping("api/doctor")
	public List<Employee> viewallDoctor(@RequestParam(defaultValue = "1") String current_page) {
		int current = Integer.parseInt(current_page);
		Page<Employee> pages = employeeRepository.findAll(PageRequest.of(current-1, 8));
		List<Employee> list = pages.getContent();
		for (int i = 0; i < list.size(); i++) {
			Long specialityId = list.get(i).getSpecialityId();
			Speciality speciality  = specialityRepository.findById(specialityId).get();
			list.get(i).setSpeciality(speciality);
		}
		return list;
	}

	@ResponseBody
	@GetMapping("api/doctor/maxpage")
	public int getMaxPage() {
		Long maxRecord = employeeRepository.count();
		int pages = (int) Math.ceil(maxRecord / 8.0);
		return pages;
	}

	@ResponseBody
	@DeleteMapping("api/doctor")
	public boolean deleteDoctor(@RequestParam String id) {
		if (id !=null) {
			Long intId = Long.valueOf(id);
			Employee employee = employeeRepository.findById(intId).get();
			if (employee !=null) {
				employeeRepository.delete(employee);
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}
