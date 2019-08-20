package com.hospital.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the role database table.
 * 
 */
@Entity
@NamedQuery(name="Role.findAll", query="SELECT r FROM Role r")
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="role_id")
	private String roleId;

	private String detail;

	@Column(name="role_code")
	private String roleCode;

	//bi-directional many-to-one association to Credential
	@OneToMany(mappedBy="role")
	private List<Credential> credentials;

	public Role() {
	}

	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getDetail() {
		return this.detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getRoleCode() {
		return this.roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public List<Credential> getCredentials() {
		return this.credentials;
	}

	public void setCredentials(List<Credential> credentials) {
		this.credentials = credentials;
	}

	public Credential addCredential(Credential credential) {
		getCredentials().add(credential);
		credential.setRole(this);

		return credential;
	}

	public Credential removeCredential(Credential credential) {
		getCredentials().remove(credential);
		credential.setRole(null);

		return credential;
	}

}