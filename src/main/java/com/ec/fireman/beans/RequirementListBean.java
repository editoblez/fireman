package com.ec.fireman.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import com.ec.fireman.data.dao.RequirementDao;
import com.ec.fireman.data.dao.RoleDao;
import com.ec.fireman.data.entities.Requirement;
import com.ec.fireman.data.entities.Role;
import com.ec.fireman.data.entities.State;

@Named
@SessionScoped
public class RequirementListBean implements Serializable {

  private static final long serialVersionUID = -5468228478359216158L;

  @Inject
  private RequirementDao requirementDao;
  @Inject
  private RoleDao roleDao;

  private List<Requirement> requirements;
  private Requirement selectedRequirement;
  private Role role;

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

  @Transactional
  public void createRequierement() {
    // TODO: SAVE
    this.refreshRequierement();
    selectedRequirement = new Requirement();
  }

  @Transactional
  public void editRequierement() {
    // TODO: SAVE
    this.refreshRequierement();
    selectedRequirement = new Requirement();
  }

  public List<Role> listRoles() {
    return roleDao.findAll();
  }

  public Requirement getSelectedRequirement() {
    return selectedRequirement;
  }

  public void setSelectedRequirement(Requirement selectedRequirement) {
    this.selectedRequirement = selectedRequirement;
  }

  public List<Requirement> getRequirements() {
    return requirements;
  }

  public void setRequirements(List<Requirement> requirements) {
    this.requirements = requirements;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

}
