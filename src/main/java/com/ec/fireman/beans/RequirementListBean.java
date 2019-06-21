package com.ec.fireman.beans;

import com.ec.fireman.data.entities.Requirement;
import com.ec.fireman.data.entities.Role;
import com.ec.fireman.data.entities.State;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class RequirementListBean implements Serializable {

	private static final long serialVersionUID = -5468228478359216158L;

	private static final Logger LOG = LogManager.getLogger(RequirementListBean.class);

	private List<Requirement> requirements;
	private Requirement selectedRequirement;
	private String role;

	@PostConstruct
	public void init() {
		this.refreshRequierement();
		selectedRequirement = new Requirement();
	}

	public void refreshRequierement() {
		// TODO:
		requirements = new ArrayList<>();
		Requirement item = new Requirement();
		item.setDescription("Descripci�n requerimiento");
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
		selectedRequirement = new Requirement();
	}

	public void editRequierement() {
		// TODO: SAVE
//		this.refreshRequierement();
		selectedRequirement = new Requirement();
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

	public Requirement getSelectedRequirement() {
		return selectedRequirement;
	}

	public void setSelectedRequirement(Requirement selectedRequirement) {
		this.selectedRequirement = selectedRequirement;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<Requirement> getRequirements() {
		return requirements;
	}

	public void setRequirements(List<Requirement> requirements) {
		this.requirements = requirements;
	}

}
