package com.hospital.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class SymptomExaminationType implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="symptom_id")
    private Long symptomId;

    @Column(name="examination_type_id")
    private Long examinationTypeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getSymptomId() {
        return symptomId;
    }

    public void setSymptomId(long symptomId) {
        this.symptomId = symptomId;
    }

    public long getExaminationTypeId() {
        return examinationTypeId;
    }

    public void setExaminationTypeId(long examinationTypeId) {
        this.examinationTypeId = examinationTypeId;
    }
}
