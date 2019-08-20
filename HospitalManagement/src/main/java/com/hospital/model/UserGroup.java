package com.hospital.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the user_group database table.
 * 
 */
@Entity
@Table(name="user_group")
@NamedQuery(name="UserGroup.findAll", query="SELECT u FROM UserGroup u")
public class UserGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_group_id")
	private String userGroupId;

	private String name;

	//bi-directional many-to-one association to Credential
	@OneToMany(mappedBy="userGroup")
	private List<Credential> credentials;

	//bi-directional many-to-one association to User
	@OneToMany(mappedBy="userGroup")
	private List<User> users;

	public UserGroup() {
	}

	public String getUserGroupId() {
		return this.userGroupId;
	}

	public void setUserGroupId(String userGroupId) {
		this.userGroupId = userGroupId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Credential> getCredentials() {
		return this.credentials;
	}

	public void setCredentials(List<Credential> credentials) {
		this.credentials = credentials;
	}

	public Credential addCredential(Credential credential) {
		getCredentials().add(credential);
		credential.setUserGroup(this);

		return credential;
	}

	public Credential removeCredential(Credential credential) {
		getCredentials().remove(credential);
		credential.setUserGroup(null);

		return credential;
	}

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public User addUser(User user) {
		getUsers().add(user);
		user.setUserGroup(this);

		return user;
	}

	public User removeUser(User user) {
		getUsers().remove(user);
		user.setUserGroup(null);

		return user;
	}

}