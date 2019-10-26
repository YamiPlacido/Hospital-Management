package com.hospital.repository;

import com.hospital.model.Appointment;
import com.hospital.model.Examination;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EmployeeRepositoryCustomImpl implements EmployeeRepositoryCustom {

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
