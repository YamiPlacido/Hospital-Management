package sent;

import com.hospital.model.*;
import com.hospital.repo.*;
import com.hospital.service.HelperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping(value = "admin/doctor")
public class DoctorController {
	List<Appointment> todayAppointment = new ArrayList<>();
	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	AppointmentRepository appointmentRepository;
	@Autowired
	AppointmentSymptomRepository appointmentSymptomRepository;
	@Autowired
	AppointmentExaminationRepository appointmentExaminationRepository;
	@Autowired
	ExaminationRepository examinationRepository;
	@Autowired
	HelperService helperService;
	//Role Doctor, view himself, today
	@RequestMapping(value = "/appointments", method = RequestMethod.GET)
	public ModelAndView viewAllTodayAppointment() {
		ModelAndView mav = new ModelAndView("doctor/doctor-appointments");
		return mav;
	}

	@ResponseBody
	@GetMapping(value = "/api/appointments/{doctor_id}")
	public List<Appointment> viewAllTodayAppointmentAPI(@PathVariable("doctor_id") int id){
		todayAppointment = employeeRepository.findAppointmentByDoctorId(id);
		return todayAppointment;
	}

	@ResponseBody
	@GetMapping(value = "/api/appointment/{app_id}")
	public Appointment viewExaminationById(@PathVariable("app_id") int id) {
		return appointmentRepository.findById(id).get();
	}
//	@GetMapping(value = "/appointment/{id}")
//	public ModelAndView doing(@PathVariable("id") int app_id) {
//		Appointment appointment = appointmentRepository.findById(app_id).get();
//		Patient patient = appointment.getPatient();
//		Age age = helperService.calculateAge(patient.getDob());
//		ModelAndView mav = new ModelAndView("doctor/doctor-appointments");
//		mav.addObject("patient",patient);
//		mav.addObject("age",age.getYears()+1);
//		mav.addObject("appointment",appointment);
//		return mav;
//	}

	//edit Appointment symptoms
	@ResponseBody
	@PostMapping("/api/symptom")
	public String editAppointmentSymptoms(
			@RequestParam("appId") int appId,
			@RequestParam(value="selectedValue[]",required = false) String[] selectedValue) {
		List<AppointmentSymptom> list = new ArrayList<>();
		AppointmentSymptom entity;

		if (selectedValue.length==0) {
			return "redirect:/doctor/appointment/"+appId;
		}
		else
			{
			list = appointmentSymptomRepository.findSymptomByAppointmentId(appId);
			for (int i = 0; i < list.size(); i++) {
				appointmentSymptomRepository.delete(list.get(i));
			}

			for (int i = 0; i < selectedValue.length; i++) {
				entity = new AppointmentSymptom();
				entity.setAppointmentId(appId);
				entity.setSymptomId(Integer.parseInt(selectedValue[i]));
				appointmentSymptomRepository.save(entity);
			}
			return "redirect:/doctor/appointment/"+appId;
		}
	}

	//edit Appointment examination
	@ResponseBody
	@PostMapping("/api/examination")
	public String editAppointmentExaminations(
			@RequestParam("appId") int appId,
			@RequestParam(value="selectedValue[]",required = false) String[] selectedValue) {
		List<AppointmentExamination> list = new ArrayList<>();
		AppointmentExamination entity;
		Examination examination = new Examination();
//		System.out.println("VAO DAY CHUA");
		if (selectedValue.length==0) {
			return "redirect:/doctor/appointment/"+appId;
		}
		else
		{
			list = appointmentExaminationRepository.findExaminationByAppointmentId(appId);
			if (list.size()>0){
				for (int i = 0; i < list.size(); i++) {
					AppointmentExamination appointmentExamination = list.get(i);
					int ex_id = appointmentExamination.getExaminationId();
					//delete old record in join table
					appointmentExaminationRepository.delete(appointmentExamination);
					//delete old exam (if status == ?)
					examinationRepository.delete(examinationRepository.findById(ex_id).get());
				}
			}

			for (int i = 0; i < selectedValue.length; i++) {
				int examination_type_id = Integer.parseInt(selectedValue[i]);
				System.out.println("EXAMINATION_TYPE_ID "+ examination_type_id);
				int examination_id;
				if (examinationRepository.findExaminationByAppointmentAndExaminationType(appId,examination_type_id).size()==0){
					int examinator_id = employeeRepository.findExaminatorsByExaminationType(examination_type_id).get(0).getEmployeeId();
					int patient_id = appointmentRepository.findById(appId).get().getPatient().getPatientId();
					System.out.println("EXAMINATOR_ID"+examinator_id+"PATIENT_ID"+patient_id);
					appointmentExaminationRepository.insertExamination(appId,examination_type_id,examinator_id,patient_id);
				} else {
//					examination=examinationRepository.findExaminationByAppointmentAndExaminationType(appId,examination_type_id).get(0);
//					examinationRepository.delete(examination);
				}
				examination_id = examinationRepository.findExaminationByAppointmentAndExaminationType(appId,examination_type_id).get(0).getId();
				entity = new AppointmentExamination();
				entity.setAppointmentId(appId);
				entity.setExaminationId(examination_id);
				appointmentExaminationRepository.save(entity);
			}
			return "redirect:/doctor/appointment/"+appId;
		}
	}

	@ResponseBody
	@GetMapping(value = "/api/appointments/symptom/{app_id}")
	public List<Symptom> getSymptomsByAppointment(@PathVariable("app_id") int id){
		return appointmentRepository.findById(id).get().getSymptoms();
	}

	@ResponseBody
	@GetMapping(value = "/api/appointments/examination/{app_id}")
	public List<Examination> getExaminationByAppointment(@PathVariable("app_id") int id){
		return appointmentRepository.findById(id).get().getExaminations();
	}

	public void editAppointmentSymptom(int id){
		todayAppointment = employeeRepository.findAppointmentByDoctorId(id);
	}

	@RequestMapping(value = "/record/{id}", method = RequestMethod.GET)
	public ModelAndView getPatientFromAppointment() {
		ModelAndView mav = new ModelAndView("doctor/patient-records");
		return mav;
	}
	//only his patient (past and current appointment) & patient himself
	@RequestMapping(value = "/record-detail/{id}", method = RequestMethod.GET)
	public ModelAndView findPatientRecord() {
		ModelAndView mav = new ModelAndView("doctor/patient-record-detail");
		return mav;
	}

	@RequestMapping(value = "/examination-detail/{id}", method = RequestMethod.GET)
	public ModelAndView viewExaminationDetail() {
		ModelAndView mav = new ModelAndView("doctor/patient-examination-detail");
		return mav;
	}


	//Role Doctor, view himself, today
	@RequestMapping(value = "/appointment-unfinish", method = RequestMethod.GET)
	public void viewUnfinishAppointment() {

	}

	//get from system
	@RequestMapping(value = "/medicine", method = RequestMethod.GET)
	public void getMedicineForIllness() {

	}

}
