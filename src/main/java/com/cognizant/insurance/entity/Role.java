package com.cognizant.insurance.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;


@Entity

public class Role {
	@Id
	private int roleID;
	private String roleName;
	public int getRoleID() {
		return roleID;
	}

	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}
