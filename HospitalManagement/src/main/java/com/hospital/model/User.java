package com.hospital.model;

import javax.persistence.*;

import java.util.List;

import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name="users")
public class User implements Serializable{
	
	private static final long serialVersionUID = -1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id", updatable = false, nullable = false)
	private long user_id;

	@Column(name = "username", nullable = false)
	private String userName;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "first_name", nullable = false)
	private String firstName;
	
	@Column(name = "last_name", nullable = false)
	private String lastName;
	
	@Column(name = "address", nullable = false)
	private String addRess;
	
	@Column(name = "dob", nullable = false)
	private Date dateofBirth;

	@Column(name = "created_date", nullable = true)
	private Date create_Date;
	
	@Column(name = "created_by", nullable = true)
	private Date create_By;
	
	@Column(name = "modified_date", nullable = true)
	private Date modified_Date;
	
	@Column(name = "modified_by", nullable = true)
	private Date modified_By;
	
	@Column
	private int status;
	@OneToMany
    @JoinColumn(name = "user_id")
	private List<User_Role> user_roles;
	
	public User() {
		 
    }
 
    public User(Long user_id, String userName, String password) {
        this.user_id = user_id;
        this.userName = userName;
        this.password = password;
    }

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public List<User_Role> getUser_roles() {
		return user_roles;
	}

	public void setUser_roles(List<User_Role> user_roles) {
		this.user_roles = user_roles;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getAddRess() {
		return addRess;
	}

	public void setAddRess(String addRess) {
		this.addRess = addRess;
	}

	public Date getDateofBirth() {
		return dateofBirth;
	}

	public void setDateofBirth(Date dateofBirth) {
		this.dateofBirth = dateofBirth;
	}

	public Date getCreate_Date() {
		return create_Date;
	}

	public void setCreate_Date(Date create_Date) {
		this.create_Date = create_Date;
	}

	public Date getCreate_By() {
		return create_By;
	}

	public void setCreate_By(Date create_By) {
		this.create_By = create_By;
	}

	public Date getModified_Date() {
		return modified_Date;
	}

	public void setModified_Date(Date modified_Date) {
		this.modified_Date = modified_Date;
	}

	public Date getModified_By() {
		return modified_By;
	}

	public void setModified_By(Date modified_By) {
		this.modified_By = modified_By;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	
}
