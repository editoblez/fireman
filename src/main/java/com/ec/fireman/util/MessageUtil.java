package com.ec.fireman.util;

import org.omnifaces.util.Messages;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.Serializable;

@ApplicationScoped
public class MessageUtil implements Serializable {

  public static void infoFacesMessage(String title, String description) {
    FacesContext.getCurrentInstance().addMessage(null,
        new FacesMessage(FacesMessage.SEVERITY_INFO, title, description));
  }

  public static void warningFacesMessage(String title, String description) {
    FacesContext.getCurrentInstance().addMessage(null,
        new FacesMessage(FacesMessage.SEVERITY_WARN, title, description));
  }

  public static void errorFacesMessage(String title, String description) {
    FacesContext.getCurrentInstance().addMessage(null,
        new FacesMessage(FacesMessage.SEVERITY_ERROR, title, description));
  }

  public static void addDetailMessage(String message) {
    addDetailMessage(message, null);
  }

  public static void addDetailMessage(String message, FacesMessage.Severity severity) {

    FacesMessage facesMessage = Messages.create("").detail(message).get();
    if (severity != null && severity != FacesMessage.SEVERITY_INFO) {
      facesMessage.setSeverity(severity);
    }
    Messages.add(null, facesMessage);
  }
}
