package com.hospital.repository;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
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
import com.hospital.dto.DoctorScheduleInforDto;
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
public class AppointmentRepositoryImpl implements AppointmentBookRepository {

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
		Query query1 = entityManager.createNativeQuery(
				"SELECT * FROM Shift s WHERE s.shift_id NOT IN (SELECT a.shift_id FROM Appointment a WHERE (a.employee_id = ?1) AND (a.date = ?2) and a.status = 1"
				+ " union all SELECT a.shift_id FROM Appointment a WHERE (a.patient_id = ?3) AND (a.date = ?2) and a.status = 1)",
				Shift.class);
		query1.setParameter(1, app.getEmployeeId()).setParameter(2, app.getDate(), TemporalType.DATE).setParameter(3, app.getPatientId());

		return query1.getResultList();
	}

	@Override
	public int checkAppBeforeAdd(AppointmentDto appointmentDto) {
		int check;
		Query query = entityManager.createNativeQuery("select COUNT(*) FROM Appointment a Where "
				+ "(a.employee_id = ?1 or a.patient_id = ?2) and a.shift_id = ?4 and a.date = ?3");
		query.setParameter(1, appointmentDto.getEmployeeId()).setParameter(2, appointmentDto.getPatientId()).setParameter(3,appointmentDto.getDate(), TemporalType.DATE)
				.setParameter(4, appointmentDto.getShiftId());
		check = ((Number) query.getSingleResult()).intValue();
		return check;
	}
	
	
	@Override
	@Modifying
	public Appointment saveApp(AppointmentDto appointmentDto) {
		Appointment app = new Appointment();
		try {
			if (appointmentDto.getAppId() == null) {
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
		} catch (Exception e) {
			e.printStackTrace();
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
			AppointmentDto app = new AppointmentDto(appId, patientName, employeeName, note, status, date);
			listApp.add(app);
		});
		AppointmentDto appExport = listApp.get(0);
		String start, end;
		if (appExport.getNote().equalsIgnoreCase("Shift 1")) {
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
		InputStream in = new FileInputStream(new File(
				"E:\\projectsem4\\HospitalManagement\\src\\main\\resources\\templates\\docs\\WaitingSlipTemplate.docx"));
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
		OutputStream out = new FileOutputStream(
				new File("E:\\projectsem4\\HospitalManagement\\src\\main\\resources\\templates\\pdf\\WaitingSlip"
						+ appExport.getPatientName() + ".pdf"));
		report.convert(context, options, out);

	}

	public List<AppointmentDto> getListAppointment() {
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
			AppointmentDto app = new AppointmentDto(appId, patientName, employeeName, note, status, date);
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
				+ "  Patient p on a.patient_id = p.patient_id where (a.date between ?1 and ?2) and a.employee_id = ?3 and a.status = 1");
		query.setParameter(1, doctorScheduleSearchDto.getDateFrom())
				.setParameter(2, addDays(doctorScheduleSearchDto.getDateFrom(),6))
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

	@Override
	public void exportSchedule(DoctorScheduleInforDto infor) throws IOException, XDocReportException {
		InputStream in = new FileInputStream(new File(
				"E:\\projectsem4\\HospitalManagement\\src\\main\\resources\\templates\\docs\\TemplateSchedule.docx"));
		IXDocReport report = XDocReportRegistry.getRegistry().loadReport(in, TemplateEngineKind.Velocity);

		IContext context = report.createContext();
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String dateFrom = simpleDateFormat.format(infor.getDateFrom());
		String dateTo = simpleDateFormat.format(addDays(infor.getDateFrom(), 6));
		String name = infor.getEmployee().getFirstName() + " " + infor.getEmployee().getLastName();
		context.put("doctorName", name);
		context.put("phone", infor.getEmployee().getPhone());
		context.put("start", dateFrom);
		context.put("end", dateTo);
		String tue = simpleDateFormat.format(addDays(infor.getDateFrom(),1));
		String wed = simpleDateFormat.format(addDays(infor.getDateFrom(),2));
		String thu = simpleDateFormat.format(addDays(infor.getDateFrom(),3));
		String fri = simpleDateFormat.format(addDays(infor.getDateFrom(),4));
		String sat = simpleDateFormat.format(addDays(infor.getDateFrom(),5));
		String sun = simpleDateFormat.format(addDays(infor.getDateFrom(),6));
		context.put("mon", dateFrom);
		context.put("tue", tue);
		context.put("wed", wed);
		context.put("thu", thu);
		context.put("fri", fri);
		context.put("sat", sat);
		context.put("sun", sun);
		for (DoctorScheduleDetailDto detail : infor.getListShift1()) {
			String textDetail = detail.getName() + " "+detail.getPhone()+ " " + detail.getIdentityCard();
			switch (detail.getDateOfWeek()) {
			case 1:
				context.put("s17", textDetail);
				break;
			case 2:
				context.put("s11", textDetail);
				break;
			case 3:
				context.put("s12", textDetail);
				break;
			case 4:
				context.put("s13", textDetail);
				break;
			case 5:
				context.put("s14", textDetail);
				break;
			case 6:
				context.put("s15", textDetail);
				break;
			default:
				context.put("s16", textDetail);
				break;
			}
		}
		for (DoctorScheduleDetailDto detail : infor.getListShift2()) {
			String textDetail = detail.getName() + " "+detail.getPhone()+ " " + detail.getIdentityCard();
			switch (detail.getDateOfWeek()) {
			case 1:
				context.put("s27", textDetail);
				break;
			case 2:
				context.put("s21", textDetail);
				break;
			case 3:
				context.put("s22", textDetail);
				break;
			case 4:
				context.put("s23", textDetail);
				break;
			case 5:
				context.put("s24", textDetail);
				break;
			case 6:
				context.put("s25", textDetail);
				break;
			default:
				context.put("s26", textDetail);
				break;
			}
		}
		for (DoctorScheduleDetailDto detail : infor.getListShift3()) {
			String textDetail = detail.getName() + " "+detail.getPhone()+ " " + detail.getIdentityCard();
			switch (detail.getDateOfWeek()) {
			case 1:
				context.put("s37", textDetail);
				break;
			case 2:
				context.put("s31", textDetail);
				break;
			case 3:
				context.put("s32", textDetail);
				break;
			case 4:
				context.put("s33", textDetail);
				break;
			case 5:
				context.put("s34", textDetail);
				break;
			case 6:
				context.put("s35", textDetail);
				break;
			default:
				context.put("s36", textDetail);
				break;
			}
		}
		for (DoctorScheduleDetailDto detail : infor.getListShift4()) {
			String textDetail = detail.getName() + " "+detail.getPhone()+ " " + detail.getIdentityCard();
			switch (detail.getDateOfWeek()) {
			case 1:
				context.put("s47", textDetail);
				break;
			case 2:
				context.put("s41", textDetail);
				break;
			case 3:
				context.put("s42", textDetail);
				break;
			case 4:
				context.put("s43", textDetail);
				break;
			case 5:
				context.put("s44", textDetail);
				break;
			case 6:
				context.put("s45", textDetail);
				break;
			default:
				context.put("s46", textDetail);
				break;
			}
		}
		for (DoctorScheduleDetailDto detail : infor.getListShift5()) {
			String textDetail = detail.getName() + " "+detail.getPhone()+ " " + detail.getIdentityCard();
			switch (detail.getDateOfWeek()) {
			case 1:
				context.put("s57", textDetail);
				break;
			case 2:
				context.put("s51", textDetail);
				break;
			case 3:
				context.put("s52", textDetail);
				break;
			case 4:
				context.put("s53", textDetail);
				break;
			case 5:
				context.put("s54", textDetail);
				break;
			case 6:
				context.put("s55", textDetail);
				break;
			default:
				context.put("s56", textDetail);
				break;
			}
		}
		Options options = Options.getTo(ConverterTypeTo.PDF);
		OutputStream out = new FileOutputStream(
				new File("E:\\projectsem4\\HospitalManagement\\src\\main\\resources\\templates\\pdf\\TemplateSchedule"
						+ infor.getEmployee().getFirstName() + ".pdf"));
		report.convert(context, options, out);

	}
	
	 public static Date addDays(Date date, int days)
	    {
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(date);
	        cal.add(Calendar.DATE, days); //minus number would decrement the days
	        return cal.getTime();
	    }
}
