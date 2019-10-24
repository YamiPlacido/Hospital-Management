package com.hospital.repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.hospital.dto.AppointmentDto;
import com.hospital.dto.DoctorScheduleDetailDto;
import com.hospital.dto.DoctorScheduleSearchDto;
import com.hospital.model.Appointment;
import com.hospital.model.Shift;

import fr.opensagres.xdocreport.converter.ConverterTypeTo;
import fr.opensagres.xdocreport.converter.Options;
import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.document.IXDocReport;
import fr.opensagres.xdocreport.document.registry.XDocReportRegistry;
import fr.opensagres.xdocreport.template.IContext;
import fr.opensagres.xdocreport.template.TemplateEngineKind;

@Repository
public class AppointmentRepositoryImpl implements AppointmentBookRepository{

	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	PatientRepository patientRepository;
	@PersistenceContext
	EntityManager entityManager;

	@Override
	@Modifying
	@SuppressWarnings("unchecked")
	public List<Shift> getListShiftAvailabelByDoctorId(AppointmentDto app) {
//		Calendar c = getTodayDate();
//		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
//		Date monday = c.getTime();
//		c.add(Calendar.DATE, 7);
//		Date sunday = c.getTime();
		Query query1 = entityManager.createNativeQuery(
				"SELECT * FROM Shift s WHERE s.shift_id NOT IN ( SELECT a.shift_id FROM Appointment a WHERE (a.employee_id = ?1) AND (a.date = ?2))",Shift.class);
		query1.setParameter(1, app.getEmployeeId()).setParameter(2, app.getDate(), TemporalType.DATE);

		return query1.getResultList();
	}

	@Override
	public int checkAppBeforeAdd(Long employee_id, Long shift_id, Date from, Date to) {
		int check;
		Query query = entityManager.createNativeQuery("COUNT(*) FROM Appointment a Where "
				+ "(a.employee_id = ?1) and (a.shift_id = ?2) and " + "(a.date between ?3 and ?4)");
		query.setParameter(1, employee_id).setParameter(2, shift_id).setParameter(3, from, TemporalType.DATE)
				.setParameter(4, to, TemporalType.DATE);
		check = ((Number) query.getSingleResult()).intValue();
		return check;
	}

	@Override
	@Modifying
	public Appointment saveApp(AppointmentDto appointmentDto) {
		Appointment app = new Appointment();
			try {
				if(appointmentDto.getAppId() == null ) {
					app.setCreatedDate(new Date());
					app.setCreatedBy("admin");
				} else {
					app.setModifiedDate(new Date());
					app.setModifiedBy("admin");
				}
				app.setEmployee(employeeRepository.findById(appointmentDto.getEmployeeId()).get());
				app.setPatient(patientRepository.findById(appointmentDto.getPatientId()).get());
				app.setStatus(appointmentDto.isStatus());
				app.setShiftId(appointmentDto.getShiftId());
				app.setDate(appointmentDto.getDate());
				app.setStage("CREATED");
				appointmentDto.setMessage("successfully");
			} catch (Exception e) {
				appointmentDto.setMessage("error");
			}
		return app;
	}
	
	@Override
	public void exportAppSlip(Long appId) throws IOException, XDocReportException {
		Query query = entityManager.createNativeQuery("SELECT "
				+ " a.app_id, a.status, p.name as patient_name, e.first_name as employee_name ,s.note, a.date FROM Appointment a left join Shift s on a.shift_id = s.shift_id left join"
				+ " Employee e on a.employee_id = e.employee_id left join Patient p on a.patient_id = p.patient_id where a.app_id = ?1");
		query.setParameter(1, appId);
		List<Object[]> results = query.getResultList();
		List<AppointmentDto> listApp = new ArrayList<>();
		results.stream().forEach((record) -> {
	        Boolean status = (Boolean) record[1];
	        String patientName = (String) record[2];
	        String employeeName = (String) record[3];
	        String note = (String) record[4];
	        Date date = (Date) record[5];
	        AppointmentDto app = new AppointmentDto(appId,patientName,employeeName,note,status,date);
	        listApp.add(app);
		});
		AppointmentDto appExport = listApp.get(0);
		String start,end;
		if(appExport.getNote().equalsIgnoreCase("Shift 1")) {
			start = "7AM";
			end = "9AM";
		} else if (appExport.getNote().equalsIgnoreCase("Shift 2")) {
			start = "9AM";
			end = "11AM";
		} else if (appExport.getNote().equalsIgnoreCase("Shift 3")) {
			start = "1PM";
			end = "3PM";
		} else if (appExport.getNote().equalsIgnoreCase("Shift 4")) {
			start = "3PM";
			end = "5PM";
		} else {
			start = "7PM";
			end = "9PM";
		}
		InputStream in = new FileInputStream(new File("E:\\projectsem4\\HospitalManagement\\src\\main\\resources\\templates\\docs\\WaitingSlipTemplate.docx"));
		IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity);

		IContext context = report.createContext();
		
		
//		FieldsMetadata metadata = new FieldsMetadata();
//		metadata.addFieldAsImage("logo");
//		report.setFieldsMetadata(metadata);
//		//IImageProvider logo = new ByteArrayImageProvider( new FileInputStream(new File("E:\\Hospital-Trang\\Hospital-Trang\\HospitalManagement3\\src\\main\\resources\\templates\\docs\\logo.PNG")), true);
//		IImageProvider logo = new FileImageProvider(new File("E:\\Hospital-Trang\\Hospital-Trang\\HospitalManagement3\\src\\main\\resources\\templates\\docs\\logo.PNG"));
//		context.put("logo", logo);
		context.put("patientName", appExport.getPatientName());
		context.put("doctorName", appExport.getEmployeeName());
		context.put("start", start);
		context.put("end", end);
		context.put("date", appExport.getDate());

		Options options = Options.getTo(ConverterTypeTo.PDF);
		OutputStream out = new FileOutputStream(new File("E:\\projectsem4\\HospitalManagement\\src\\main\\resources\\templates\\pdf\\WaitingSlip"+appExport.getPatientName()+".pdf"));
		report.convert(context, options, out);

	}

	public List<AppointmentDto> getListAppointment(){
		List<AppointmentDto> listApp = new ArrayList<>();
		Query query = entityManager.createNativeQuery("SELECT "
				+ " a.app_id, a.status, p.name as patient_name, e.first_name as employee_name ,s.note, a.date FROM Appointment a left join Shift s on a.shift_id = s.shift_id left join"
				+ " Employee e on a.employee_id = e.employee_id left join Patient p on a.patient_id = p.patient_id");
		List<Object[]> results = query.getResultList();
		
		results.stream().forEach((record) -> {
	        Long appId = ((Integer) record[0]).longValue();
	        Boolean status = (Boolean) record[1];
	        String patientName = (String) record[2];
	        String employeeName = (String) record[3];
	        String note = (String) record[4];
	        Date date = (Date) record[5];
	        AppointmentDto app = new AppointmentDto(appId,patientName,employeeName,note,status,date);
	        listApp.add(app);
	});
		return listApp;
	}
	private Calendar getTodayDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal;
	}

	@Override
	public List<DoctorScheduleDetailDto> getDoctorSchedule(DoctorScheduleSearchDto doctorScheduleSearchDto) {
		List<DoctorScheduleDetailDto> listDetail = new ArrayList();
		Query query = entityManager.createNativeQuery("SELECT "
				+ " a.app_id, a.status, p.name , p.identity_card, p.phone, a.shift_id, a.patient_id, a.date FROM Appointment a left join Shift s on a.shift_id = s.shift_id left join"
				+ "  Patient p on a.patient_id = p.patient_id where (a.date between ?1 and ?2) and a.employee_id = ?3");
		query.setParameter(1, doctorScheduleSearchDto.getDateFrom()).setParameter(2, doctorScheduleSearchDto.getDateTo())
		.setParameter(3, doctorScheduleSearchDto.getEmployeeId());
		List<Object[]> results = query.getResultList();
		
		
		results.stream().forEach((record) -> {
	        Long appId = ((Integer) record[0]).longValue();
	        Boolean status = (Boolean) record[1];
	        String patientName = (String) record[2];
	        String identityCard = (String) record[3];
	        String phone = (String) record[4];
	        Long shiftId = ((Integer) record[5]).longValue();
	        Long patientId = ((Integer) record[6]).longValue();
	        Date date = (Date) record[7];
	        DoctorScheduleDetailDto detail = new DoctorScheduleDetailDto();
	        detail.setAppId(appId);
	        detail.setDate(date);
	        detail.setIdentityCard(identityCard);
	        detail.setName(patientName);
	        detail.setShiftId(shiftId);
	        detail.setStatus(status);
	        detail.setPhone(phone);
	        detail.setPatientId(patientId);
	        listDetail.add(detail);
		});
		return listDetail;
		
	}
}
