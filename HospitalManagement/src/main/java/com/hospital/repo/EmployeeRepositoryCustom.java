package com.hospital.repo;

import com.hospital.entity.Appointment;
import com.hospital.entity.Examination;

import java.util.List;

public interface EmployeeRepositoryCustom {
    public List<Examination> findExaminationsByExaminatorId(int employee_id);
    public List<Examination> findUnfinishedExaminationsByExaminatorId(int employee_id);
    public List<Appointment> findAppointmentByDoctorId(int employee_id);
}
