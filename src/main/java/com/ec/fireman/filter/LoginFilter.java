package com.ec.fireman.filter;

import com.ec.fireman.beans.PageNameConstants;
import org.omnifaces.filter.HttpFilter;
import org.omnifaces.util.Servlets;

import javax.faces.application.ResourceHandler;
import javax.faces.context.FacesContext;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

public class LoginFilter extends HttpFilter {

  private static final String INDEX_PAGE = PageNameConstants.HOME_PAGE;
  private static final String LOGIN_PAGE = "app/login.xhtml";
  private static final Locale DEFAULT_LOCALE = new Locale("es");

  @Override
  public void doFilter(HttpServletRequest request, HttpServletResponse response,
                       HttpSession session, FilterChain chain) throws ServletException, IOException {
    request.setCharacterEncoding(StandardCharsets.UTF_8.name());
    boolean resourceRequest = request.getRequestURI().startsWith(request.getContextPath() + ResourceHandler.RESOURCE_IDENTIFIER);
    boolean richRequest = request.getRequestURI().startsWith(request.getContextPath() + "/faces/rfRes");
    updateCurrentLocale();
    if (isAuthenticated(session) && (request.getRequestURI().contains("login"))) {
      Servlets.facesRedirect(request, response, INDEX_PAGE);
      return;
    }
    if (isAuthenticated(session) || urlIsAllowed(request) || resourceRequest || richRequest) {
      chain.doFilter(request, response);
      return;
    }
    Servlets.facesRedirect(request, response, LOGIN_PAGE);
  }

  private void updateCurrentLocale() {
    if (FacesContext.getCurrentInstance() != null && FacesContext.getCurrentInstance().getViewRoot() != null) {
      FacesContext.getCurrentInstance().getViewRoot().setLocale(DEFAULT_LOCALE);
    }
  }

  private boolean isAuthenticated(HttpSession session) {
    return session != null && session.getAttribute("userid") != null;
  }

  private boolean urlIsAllowed(HttpServletRequest request) {
    String url = request.getRequestURL().toString();
    return url.contains("login") || url.contains("register-client");
  }

}

