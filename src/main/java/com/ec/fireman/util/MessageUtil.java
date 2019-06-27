package com.ec.fireman.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class MessageUtil {

  public static void sendFacesMessage(String title, String description) {
    FacesMessage message = new FacesMessage(title, description);
    FacesContext.getCurrentInstance().addMessage(null, message);
  }
}
