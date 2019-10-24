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
public class UserRole implements Serializable{
	
	private static final long serialVersionUID = -1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ur_id", updatable = false, nullable = false)
	private long urId;
	
	@Column(name = "user_id", nullable = true)
	private long userId;
	
	@Column(name = "role_id", nullable = true)
	private long roleId;

	public long getUr_id() {
		return urId;
	}

	public long getUrId() {
		return urId;
	}

	public void setUrId(long urId) {
		this.urId = urId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

}
