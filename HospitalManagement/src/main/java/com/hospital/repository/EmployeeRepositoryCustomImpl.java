package com.hospital.repository;

import com.hospital.dto.PatientRecordDTO;
import com.hospital.model.Appointment;
import com.hospital.model.Examination;
import com.hospital.model.Symptom;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EmployeeRepositoryCustomImpl implements EmployeeRepositoryCustom {

    @Autowired
    PatientRepository patientRepository;
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Examination> findExaminationsByExaminatorId(Long employee_id) {
        Calendar cal = getTodayDate();
        Date realDay = cal.getTime();
        cal.add(Calendar.DATE, +1);
        Date dateAfter1Days = cal.getTime();
        Query query = entityManager.createNativeQuery
                ("SELECT * FROM Examination a WHERE (a.examinator_id = ?1) AND (a.date >= ?2) AND (a.date < ?3)",
                        Examination.class);
        query.setParameter(1, employee_id)
        .setParameter(2,realDay, TemporalType.DATE)
        .setParameter(3,dateAfter1Days, TemporalType.DATE);
        List<Examination> result = query.getResultList();
        for (int i = 0; i < result.size(); i++) {
            cal.setTime(result.get(i).getDate());
            cal.add(Calendar.HOUR,8);
            result.get(i).setDate(cal.getTime());
        }
        return result;
    }

    @Override
    public List<Examination> findUnfinishedExaminationsByExaminatorId(Long employee_id) {
        Calendar cal = getTodayDate();
        Date realDay = cal.getTime();
        cal.add(Calendar.DATE, +1);
        Date dateAfter1Days = cal.getTime();
        Query query = entityManager.createNativeQuery
                ("SELECT * FROM Examination a WHERE (a.examinator_id = ?1) AND (a.date >= ?2) AND (a.date < ?3) " +
                                "AND a.stage <> 'FINISHED'",
                        Examination.class);
        query.setParameter(1, employee_id)
                .setParameter(2,realDay, TemporalType.DATE)
                .setParameter(3,dateAfter1Days, TemporalType.DATE);
        List<Examination> result = query.getResultList();
        for (int i = 0; i < result.size(); i++) {
            cal.setTime(result.get(i).getDate());
            cal.add(Calendar.HOUR,8);
            result.get(i).setDate(cal.getTime());
        }
        return result;
    }

    @Override
    public List<Appointment> findAppointmentByDoctorId(Long employee_id) {
        Calendar cal = getTodayDate();

        Date realDay = cal.getTime();
        cal.add(Calendar.DATE, +1);
        Date dateAfter1Days = cal.getTime();
        Query query = entityManager.createNativeQuery
                ("SELECT * FROM Appointment a WHERE (a.employee_id = ?1) AND (a.date >= ?2) AND (a.date < ?3)",
                        Appointment.class);
        query.setParameter(1, employee_id)
                .setParameter(2,realDay, TemporalType.DATE)
                .setParameter(3,dateAfter1Days, TemporalType.DATE);
        List<Appointment> result = query.getResultList();
        for (int i = 0; i < result.size(); i++) {
            cal.setTime(result.get(i).getDate());
            cal.add(Calendar.HOUR,8);
            result.get(i).setDate(cal.getTime());
        }
        return result;
    }

    @Override
    public PatientRecordDTO findPatientHistoryRecord(Long patient_id) {
        Query query;
        PatientRecordDTO patientRecordDTO = new PatientRecordDTO();
        patientRecordDTO.setPatient(patientRepository.findById(patient_id).get());
        query = entityManager.createNativeQuery
                ("SELECT * FROM Appointment a WHERE (a.patient_id = ?1) AND a.stage = 'FINISHED'",
                        Appointment.class);
        query.setParameter(1, patient_id);
        patientRecordDTO.setAppointments(query.getResultList());
        query = entityManager.createNativeQuery
                ("SELECT symptom, COUNT(*) FROM Appointment a " +
                                "INNER JOIN appointment_symptom app_symp " +
                                "    on a.app_id = app_symp.app_id " +
                                "INNER JOIN symptom s " +
                                "    on app_symp.symptom_id = s.symptom_id" +
                                "WHERE (a.patient_id = ?1) AND a.stage = 'FINISHED' " +
                                "GROUP BY symptom " +
                                "ORDER BY COUNT(*) DESC",
                        Symptom.class);
        query.setParameter(1, patient_id);
        patientRecordDTO.setSymptoms(query.getResultList());
        return patientRecordDTO;
    }


    private Calendar getTodayDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND,0);
        return cal;
    }
}
