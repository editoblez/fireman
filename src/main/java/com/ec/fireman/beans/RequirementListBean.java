package com.ec.fireman.beans;

import com.ec.fireman.data.dao.RequirementDao;
import com.ec.fireman.data.dao.RoleDao;
import com.ec.fireman.data.entities.Requirement;
import com.ec.fireman.data.entities.Role;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

@Data
@Log4j2
@Named
@ViewScoped
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
    this.refreshRequirement();
    selectedRequirement = new Requirement();
  }

  public void refreshRequirement() {
    requirements = requirementDao.findAll();
    log.info("Requirements length: " + requirements != null ? requirements.size() : 0);
  }

  @Transactional
  public void createRequirement() {
    selectedRequirement.setRole(role);
    requirementDao.save(selectedRequirement);
    this.refreshRequirement();
    selectedRequirement = new Requirement();
  }

  @Transactional
  public void editRequirement() {
    selectedRequirement.setRole(role);
    requirementDao.update(selectedRequirement);
    this.refreshRequirement();
    selectedRequirement = new Requirement();
  }

  public List<Role> listRoles() {
    return roleDao.findAll();
  }

}
