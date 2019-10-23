package com.hospital.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
public class Role implements Serializable{
	
	private static final long serialVersionUID = -1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_id", updatable = false, nullable = false)
	private long role_id;

	@Column (name = "role_name", nullable = false)
	private String name;
	
	@Column (name = "description", nullable = false)
	private String description;

	@Column(name = "created_date", nullable = true)
	private Date create_Date;
	
	@Column(name = "created_by", nullable = true)
	private Date create_By;
	
	@Column(name = "modified_date", nullable = true)
	private Date modified_Date;
	
	@Column(name = "modified_by", nullable = true)
	private Date modified_By;
	
	@OneToMany
    @JoinColumn(name = "role_id")
	private List<User_Role> user_roles;

	public List<User_Role> getUser_roles() {
		return user_roles;
	}

	public void setUser_roles(List<User_Role> user_roles) {
		this.user_roles = user_roles;
	}

	public long getRole_id() {
		return role_id;
	}

	public void setRole_id(long role_id) {
		this.role_id = role_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
	
	
}
