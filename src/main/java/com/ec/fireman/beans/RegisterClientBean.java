package com.ec.fireman.beans;

import com.ec.fireman.data.dao.RoleDao;
import com.ec.fireman.data.dao.UserAccountDao;
import com.ec.fireman.data.entities.Role;
import com.ec.fireman.data.entities.RoleTypes;
import com.ec.fireman.data.entities.UserAccount;
import com.ec.fireman.util.PasswordUtil;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;

@Data
@Log4j2
@Named
@ViewScoped
public class RegisterClientBean implements Serializable {

  @Inject
  private UserAccountDao userAccountDao;

  @Inject
  private RoleDao roleDao;

  private String firstName;
  private String secondName;
  private String firstLastName;
  private String secondLastName;
  private String ci;
  private String email;

  @Transactional
  public String register() {
    Role role = roleDao.findRolByName(RoleTypes.CLIENT);
    if (role == null) {
      log.error("Error to execute findRoleByName, the role don't exists in db");
      return PageNameConstants.REGISTER_CLIENT_PAGE;
    }
    log.debug("Registering a user: " + toString());
    UserAccount client = new UserAccount(firstName, secondName, firstLastName, secondLastName, ci,
        PasswordUtil.encrypt(ci), email, role);
    userAccountDao.save(client);
    return PageNameConstants.LOGIN_PAGE;
  }

}
