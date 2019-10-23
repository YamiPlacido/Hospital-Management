package com.hospital.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_role")
public class User_Role implements Serializable{
	
	private static final long serialVersionUID = -1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ur_id", updatable = false, nullable = false)
	private long ur_id;
	
	@Column(name = "user_id", nullable = true)
	private long user_id;
	
	@Column(name = "role_id", nullable = true)
	private long role_id;

	public long getUr_id() {
		return ur_id;
	}

	public void setUr_id(long ur_id) {
		this.ur_id = ur_id;
	}

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public long getRole_id() {
		return role_id;
	}

	public void setRole_id(long role_id) {
		this.role_id = role_id;
	}
	
	
}
