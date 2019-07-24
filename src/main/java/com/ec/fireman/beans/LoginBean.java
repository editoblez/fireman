package com.ec.fireman.beans;

import com.ec.fireman.data.dao.UserAccountDao;
import com.ec.fireman.data.entities.UserAccount;
import com.ec.fireman.util.PasswordUtil;
import com.ec.fireman.util.SessionUtils;
import com.ec.fireman.util.UriUtil;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Servlets;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;

import static com.ec.fireman.beans.PageNameConstants.HOME_PAGE;
import static com.ec.fireman.beans.PageNameConstants.LOGIN_PAGE;

@Data
@Log4j2
@Named
@SessionScoped
public class LoginBean implements Serializable {

  public static final String LOGIN_ERROR_MESSAGES = "Usuario o clave inválida ";

  @Inject
  private UserAccountDao userAccountDao;

  private String ci;
  private String password;

  private String email;

  public String validateUserAndPassword() throws IOException {
    log.debug("attempt to login the user: " + ci);
    UserAccount account = userAccountDao.findUserByCi(ci);

    if (account == null) {
      log.debug("The user do not exists");
      FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login Error", LOGIN_ERROR_MESSAGES));
      return LOGIN_PAGE;
    }

    if (PasswordUtil.encrypt(password).equals(account.getPassword())) {
      log.debug("Authentication successful for user: " + ci);
      SessionUtils.saveLoggingInfo(account.getCi(), account.getRole().getRoleName(), account.getEmail());
      Servlets.facesRedirect(SessionUtils.getRequest(), SessionUtils.getResponse(), UriUtil.removeStaringSlash(HOME_PAGE));
      return HOME_PAGE;
    }
    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login Error", LOGIN_ERROR_MESSAGES));
    return LOGIN_PAGE;
  }

  public String loggedUser() {
    return Objects.requireNonNull(SessionUtils.retrieveLoggedUser()).getUserId();
  }

  public String emailUser() {
    return Faces.getSessionAttribute("email");
  }

}
