package com.hospital.repository;

import com.hospital.model.Appointment;
import com.hospital.model.Examination;

import java.util.List;

public interface EmployeeRepositoryCustom {
    public List<Examination> findExaminationsByExaminatorId(Long employee_id);
    public List<Examination> findUnfinishedExaminationsByExaminatorId(Long employee_id);
    public List<Appointment> findAppointmentByDoctorId(Long employee_id);
}
