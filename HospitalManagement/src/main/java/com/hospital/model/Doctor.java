package com.hospital.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the doctor database table.
 * 
 */
@Entity
@NamedQuery(name="Doctor.findAll", query="SELECT d FROM Doctor d")
public class Doctor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="doctor_id")
	private int doctorId;

	private String address;

	private String code;

	@Temporal(TemporalType.DATE)
	private Date dob;

	@Column(name="image_path")
	private String imagePath;

	private String name;

	private String note;

	private String phone;

	private String status;

	//bi-directional many-to-one association to Appointment
	@OneToMany(mappedBy="doctor")
	private List<Appointment> appointments;

	//bi-directional many-to-one association to DoctorPosition
	@ManyToOne
	@JoinColumn(name="position_id")
	private DoctorPosition doctorPosition;

	//bi-directional many-to-one association to Speciality
	@ManyToOne
	@JoinColumn(name="speciality_id")
	private Speciality speciality;

	public Doctor() {
	}

	public int getDoctorId() {
		return this.doctorId;
	}

	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getDob() {
		return this.dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getImagePath() {
		return this.imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Appointment> getAppointments() {
		return this.appointments;
	}

	public void setAppointments(List<Appointment> appointments) {
		this.appointments = appointments;
	}

	public Appointment addAppointment(Appointment appointment) {
		getAppointments().add(appointment);
		appointment.setDoctor(this);

		return appointment;
	}

	public Appointment removeAppointment(Appointment appointment) {
		getAppointments().remove(appointment);
		appointment.setDoctor(null);

		return appointment;
	}

	public DoctorPosition getDoctorPosition() {
		return this.doctorPosition;
	}

	public void setDoctorPosition(DoctorPosition doctorPosition) {
		this.doctorPosition = doctorPosition;
	}

	public Speciality getSpeciality() {
		return this.speciality;
	}

	public void setSpeciality(Speciality speciality) {
		this.speciality = speciality;
	}

}