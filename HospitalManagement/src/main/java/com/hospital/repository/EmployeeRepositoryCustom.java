package com.hospital.repository;

import java.util.List;

import com.hospital.dto.PatientRecordDTO;
import com.hospital.model.Appointment;
import com.hospital.model.Examination;

public interface EmployeeRepositoryCustom {
    public List<Examination> findExaminationsByExaminatorId(Long employee_id);
    public List<Examination> findUnfinishedExaminationsByExaminatorId(Long employee_id);
    public List<Examination> findAllTodayExaminationsByExaminatorId(Long employee_id);
    public List<Appointment> findAppointmentByDoctorId(Long employee_id);
//    public PatientRecordDTO findPatientHistoryRecord(Long patient_id);
}
