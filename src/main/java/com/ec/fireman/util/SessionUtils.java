package com.ec.fireman.util;

import com.ec.fireman.data.representation.User;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionUtils {

  public static final String USER_ID = "userid";
  public static final String ROL = "rol";

  public static HttpSession getSession() {
    return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
  }

  public static HttpServletRequest getRequest() {
    return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
  }

  public static User retrieveLoggedUser() {
    HttpSession session = getSession();
    if (session != null) {
      return new User((String) session.getAttribute(USER_ID), (String) session.getAttribute(ROL));
    }
    return null;
  }

  public static void saveLoggingInfo(String userId, String rol) {
    HttpSession session = getSession();
    if (session != null) {
      session.setAttribute(USER_ID, userId);
      session.setAttribute(ROL, rol);
    }
  }

  public static void closeSession() {
    HttpSession session = getSession();
    if (session != null) {
      session.removeAttribute(USER_ID);
      session.removeAttribute(ROL);
      session.invalidate();
    }
  }
}
