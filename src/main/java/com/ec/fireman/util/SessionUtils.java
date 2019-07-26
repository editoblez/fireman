package com.ec.fireman.util;

import com.ec.fireman.data.entities.RoleTypes;
import com.ec.fireman.data.representation.User;
import lombok.extern.log4j.Log4j2;
import org.omnifaces.util.Faces;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;

@Log4j2
public class SessionUtils {

  private static final String USER_ID = "userid";
  public static final String ROLE = "rol";
  public static final String USER_EMAIL = "email";

  public static HttpServletRequest getRequest() {
    return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
  }

  public static HttpServletResponse getResponse() {
    return (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
  }

  public static User retrieveLoggedUser() {
    String roleFromSession = Faces.getSessionAttribute(ROLE);
    if (roleFromSession == null) {
      return null;
    }
    Optional<RoleTypes> roleType = Arrays.stream(RoleTypes.values()).filter(it -> it.getValue().equals(roleFromSession)).findFirst();
    return roleType.map(role -> new User(Faces.getSessionAttribute(USER_ID), role)).orElse(null);
  }

  public static void saveLoggingInfo(String userId, RoleTypes role) {
    Faces.setSessionAttribute(USER_ID, userId);
    Faces.setSessionAttribute(ROLE, role.getValue());
  }

  public static void saveLoggingInfo(String userId, RoleTypes role, String email) {
    Faces.setSessionAttribute(USER_ID, userId);
    Faces.setSessionAttribute(ROLE, role.getValue());
    Faces.setSessionAttribute(USER_EMAIL, email);
  }

  public static void closeSession() {
    Faces.invalidateSession();
  }
}
