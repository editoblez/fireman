package com.ec.fireman.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import com.ec.fireman.data.dao.RoleDao;
import com.ec.fireman.data.dao.UserAccountDao;
import com.ec.fireman.data.entities.Role;
import com.ec.fireman.data.entities.UserAccount;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Data
@Log4j2
@Named
@SessionScoped
public class UserListBean implements Serializable {

  private static final long serialVersionUID = -5468228478359216158L;

  private static final String MSG_ERROR_PASSWORD = "Las contraseñas no coinciden";

  @Inject
  private UserAccountDao userAccountDao;
  @Inject
  private RoleDao roleDao;

  private List<UserAccount> users;
  private UserAccount selectedUser;
  private Role role;
  private String passReset1;
  private String passReset2;

  @PostConstruct
  public void init() {
    this.refreshUsers();
    selectedUser = new UserAccount();
  }

  public void refreshUsers() {
    users = userAccountDao.findAll();
    log.info("Users length: " + (users != null ? users.size() : 0));
  }

  @Transactional
  public void createUser() {
    selectedUser.setRole(role);
    userAccountDao.save(selectedUser);
    this.refreshUsers();
    selectedUser = new UserAccount();
  }

  @Transactional
  public void editUser() {
    selectedUser.setRole(role);
    userAccountDao.update(selectedUser);
    this.refreshUsers();
    selectedUser = new UserAccount();
  }

  @Transactional
  public String resetPassword() {
    if (!passReset1.equals(passReset2)) {
      return MSG_ERROR_PASSWORD;
    }
    selectedUser.setPassword(passReset1);
    userAccountDao.update(selectedUser);
    selectedUser = new UserAccount();
    return "OK";
  }

  public List<Role> listRoles() {
    return roleDao.findAll();
  }

}
