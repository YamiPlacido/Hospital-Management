package com.hospital.repository;

import com.hospital.model.Appointment;
import com.hospital.model.Examination;

import java.util.List;

public interface EmployeeRepositoryCustom {
    public List<Examination> findExaminationsByExaminatorId(int employee_id);
    public List<Examination> findUnfinishedExaminationsByExaminatorId(int employee_id);
    public List<Appointment> findAppointmentByDoctorId(int employee_id);
}
