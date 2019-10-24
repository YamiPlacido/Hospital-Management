package com.hospital.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class AppointmentExamination implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private long id;

    @Column(name="app_id")
    private long appointmentId;

    @Column(name="ex_id")
    private long examinationId;

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public long getExaminationId() {
        return examinationId;
    }

    public void setExaminationId(long examinationId) {
        this.examinationId = examinationId;
    }
}
