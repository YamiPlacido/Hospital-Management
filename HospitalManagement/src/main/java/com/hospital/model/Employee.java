package com.hospital.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Employee implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="employee_id")
	private int employeeId;
	
	@NotNull(message = "First name cannot be null")
	@Size(min = 2, max = 50, message 
    = "Address must be between 2 and 50 characters")
	private String firstName;
	
	@NotEmpty(message = "Last name cannot be null")
	private String lastName;
	
//	@Size(min = 6, max = 50, message
//		    = "Password must be between 6 and 50 characters")
//	private String password;
	
	@Size(min = 5, max = 100, message 
		      = "Address must be between 5 and 100 characters")
	private String address;

	private String code;

	@Email(message = "Email should be valid")
	private String email;
	
	private Character gender;
	
	@Temporal(TemporalType.DATE)
	@DateTimeFormat (pattern="YYYY-MM-dd")
	private Date dob;

	@Column(name="image_path")
	private String imagePath;
	
	private String note;

	private String phone;

	private String status;
	
	//bi-directional many-to-one association to DoctorPosition
	@ManyToOne
	@JoinColumn(name="position_id")
	private Position position;

	//bi-directional many-to-one association to Speciality
	@ManyToOne
	@JoinColumn(name="speciality_id")
	private Speciality speciality;

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Speciality getSpeciality() {
		return speciality;
	}

	public void setSpeciality(Speciality speciality) {
		this.speciality = speciality;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Character getGender() {
		return gender;
	}

	public void setGender(Character gender) {
		this.gender = gender;
	}
	
}
