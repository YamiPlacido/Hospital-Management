package com.hospital.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the credential database table.
 * 
 */
@Entity
@NamedQuery(name="Credential.findAll", query="SELECT c FROM Credential c")
public class Credential implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="credential_id")
	private String credentialId;

	//bi-directional many-to-one association to Role
	@ManyToOne
	@JoinColumn(name="role_id")
	private Role role;

	//bi-directional many-to-one association to UserGroup
	@ManyToOne
	@JoinColumn(name="user_group_id")
	private UserGroup userGroup;

	public Credential() {
	}

	public String getCredentialId() {
		return this.credentialId;
	}

	public void setCredentialId(String credentialId) {
		this.credentialId = credentialId;
	}

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public UserGroup getUserGroup() {
		return this.userGroup;
	}

	public void setUserGroup(UserGroup userGroup) {
		this.userGroup = userGroup;
	}

}