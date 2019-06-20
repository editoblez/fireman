package com.ec.fireman.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ec.fireman.data.entities.Requierement;
import com.ec.fireman.data.entities.Role;
import com.ec.fireman.data.entities.State;

@Named
@SessionScoped
public class RequirementListBean implements Serializable {

	private static final long serialVersionUID = -5468228478359216158L;

	private static final Logger LOG = LogManager.getLogger(RequirementListBean.class);

	private List<Requierement> requirements;
	private Requierement selectedRequierement;
	private String role;

	@PostConstruct
	public void init() {
		this.refreshRequierement();
		selectedRequierement = new Requierement();
	}

	public void refreshRequierement() {
		// TODO:
		requirements = new ArrayList<>();
		Requierement item = new Requierement();
		item.setDescription("Descripción requerimiento");
		item.setName("Nombre requerimiento");
		item.setState(State.ACTIVE);
		Role role = new Role();
		role.setRoleName("Administrador");
		item.setRole(role);
		requirements.add(item);
	}

	public void createRequierement() {
		// TODO: SAVE
//		this.refreshRequierement();
		selectedRequierement = new Requierement();
	}

	public void editRequierement() {
		// TODO: SAVE
//		this.refreshRequierement();
		selectedRequierement = new Requierement();
	}

	public List<String> listRoles() {
		// TODO: SEARCH THE ROLES IN THE DATABASE
		List<String> roles = new ArrayList<String>();
		roles.add("Administrador");
		roles.add("Inspector");
		roles.add("Contador");
		roles.add("Bombero");
		return roles;
	}

	public Requierement getSelectedRequierement() {
		return selectedRequierement;
	}

	public void setSelectedRequierement(Requierement selectedRequierement) {
		this.selectedRequierement = selectedRequierement;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<Requierement> getRequirements() {
		return requirements;
	}

	public void setRequirements(List<Requierement> requirements) {
		this.requirements = requirements;
	}

}
