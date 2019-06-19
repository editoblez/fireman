package com.ec.fireman.beans;

import com.ec.fireman.data.representation.User;
import com.ec.fireman.util.SessionUtils;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;

@Data

@Log4j2
@Named
@SessionScoped
public class LogoutBean implements Serializable {

  public String logout() {
    log.debug("Closing the session");
    User loggedUser = SessionUtils.retrieveLoggedUser();
    if (loggedUser == null || loggedUser.getUserId() == null) {
      log.error("There are no active session");
      return "login";
    }
    SessionUtils.closeSession();
    return "login";
  }
}
