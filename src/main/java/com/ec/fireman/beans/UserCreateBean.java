package com.ec.fireman.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Named
@SessionScoped
public class UserCreateBean implements Serializable {

	private static final long serialVersionUID = 1386092170909655771L;
	private static final Logger LOG = LogManager.getLogger(UserCreateBean.class);

	private String ci;
	private String name;
	private String email;
	private String role;
	private List<String> roles;

	@PostConstruct
    public void init() {
		
		roles = new ArrayList<String>();
		roles.add("Adminsitrador");
		roles.add("Inspector");
		roles.add("Contador");
		roles.add("Bombero");
	}
	
	public void saveUser() {
		LOG.info(ci + " " + name + " " + email + " " + role);
	}

	public String getCi() {
		return ci;
	}

	public void setCi(String ci) {
		this.ci = ci;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

}
