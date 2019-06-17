package com.ec.fireman.beans;

import com.ec.fireman.data.dao.UserAccountDao;
import com.ec.fireman.data.entities.UserAccount;
import com.ec.fireman.util.PasswordUtil;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

import static com.ec.fireman.beans.PageNameConstants.ADMIN_LOGIN_PAGE;
import static com.ec.fireman.beans.PageNameConstants.LOGIN_PAGE;

@Named
@SessionScoped
public class LoginBean implements Serializable {

  public static final String LOGIN_ERROR_MESSAGES = "Usuario o clave inválida ";
  @Inject
  private UserAccountDao userAccountDao;

  private String ci;
  private String password;

  public String validateUserAndPassword() {
    UserAccount account = userAccountDao.findUserByCi(ci);

    if (account == null) {
      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login Error", LOGIN_ERROR_MESSAGES));
      return LOGIN_PAGE;
    }

    if (PasswordUtil.encrypt(password).equals(account.getPassword())) {
      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login Error", LOGIN_ERROR_MESSAGES));
      return ADMIN_LOGIN_PAGE;
    }

    return LOGIN_PAGE;
  }

  public String getCi() {
    return ci;
  }

  public void setCi(String ci) {
    this.ci = ci;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
