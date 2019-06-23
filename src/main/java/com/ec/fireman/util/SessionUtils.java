package com.ec.fireman.util;

import com.ec.fireman.data.representation.User;
import lombok.extern.log4j.Log4j2;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Log4j2
public class SessionUtils {

  public static final String USER_ID = "userid";
  public static final String ROL = "rol";

  public static HttpSession getSession() {
    return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
  }

  public static HttpServletRequest getRequest() {
    return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
  }

  public static HttpServletResponse getResponse() {
    return (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
  }

  public static ExternalContext retrieveCurrentJsfContext() {
    return FacesContext.getCurrentInstance().getExternalContext();
  }

  public static String buildJsfUrl(String url) {
    return retrieveCurrentJsfContext().getRequestContextPath() + url;
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
      return;
    }
    log.error("There are no session");
  }

  public static void closeSession() {
    HttpSession session = getSession();
    if (session != null) {
      session.removeAttribute(USER_ID);
      session.removeAttribute(ROL);
      session.invalidate();
      return;
    }
    log.error("There are no session");
  }
}
