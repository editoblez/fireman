package com.ec.fireman.beans;

import com.ec.fireman.data.dao.RoleDao;
import com.ec.fireman.data.dao.UserAccountDao;
import com.ec.fireman.data.entities.Role;
import com.ec.fireman.data.entities.UserAccount;
import com.ec.fireman.util.PasswordUtil;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;

import static com.ec.fireman.beans.PageNameConstants.LOGIN_PAGE;

@Data
@Log4j2
@Named
@ViewScoped
public class RegisterClientBean implements Serializable {

  public static final String CLIENT_ROLE = "client";
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
    Role role = roleDao.findRolByName(CLIENT_ROLE);
    log.debug("Registering a user: " + toString());
    UserAccount client = new UserAccount(firstName, secondName, firstLastName, secondLastName, ci,
        PasswordUtil.encrypt(ci), email, role == null ? new Role(CLIENT_ROLE) : role);
    userAccountDao.save(client);
    return LOGIN_PAGE;
  }

}
