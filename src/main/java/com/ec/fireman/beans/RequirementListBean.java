package com.ec.fireman.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ec.fireman.data.dao.RoleDao;
import com.ec.fireman.data.entities.Requierement;
import com.ec.fireman.data.entities.Role;
import com.ec.fireman.data.entities.State;

@Named
@SessionScoped
public class RequirementListBean implements Serializable {

	private static final long serialVersionUID = -5468228478359216158L;

	private static final Logger LOG = LogManager.getLogger(RequirementListBean.class);

	// TODO: IMPORT DAO
	@Inject
	private RoleDao roleDao;

	private List<Requierement> requirements;
	private Requierement selectedRequierement;
	private Role role;

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

	@Transactional
	public void createRequierement() {
		// TODO: SAVE
		this.refreshRequierement();
		selectedRequierement = new Requierement();
	}

	@Transactional
	public void editRequierement() {
		// TODO: SAVE
		this.refreshRequierement();
		selectedRequierement = new Requierement();
	}

	public List<Role> listRoles() {
		return roleDao.findAll();
	}

	public Requierement getSelectedRequierement() {
		return selectedRequierement;
	}

	public void setSelectedRequierement(Requierement selectedRequierement) {
		this.selectedRequierement = selectedRequierement;
	}

	public List<Requierement> getRequirements() {
		return requirements;
	}

	public void setRequirements(List<Requierement> requirements) {
		this.requirements = requirements;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
