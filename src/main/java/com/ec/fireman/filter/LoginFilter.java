package com.ec.fireman.filter;

import org.omnifaces.filter.HttpFilter;
import org.omnifaces.util.Servlets;

import javax.faces.context.FacesContext;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

@WebFilter("/app/*")
public class LoginFilter extends HttpFilter {

  public static final String INDEX_PAGE = "app/index.xhtml";
  public static final String LOGIN_PAGE = "app/login.xhtml";
  private static final Locale DEFAULT_LOCALE = new Locale("es");

  @Override
  public void doFilter(HttpServletRequest request, HttpServletResponse response,
                       HttpSession session, FilterChain chain) throws ServletException, IOException {
    request.setCharacterEncoding(StandardCharsets.UTF_8.name());
    updateCurrentLocale();
    if (isAuthenticated(session) && (request.getRequestURI().contains("login"))) {
      Servlets.facesRedirect(request, response, INDEX_PAGE);
      return;
    }
    if (isAuthenticated(session) || urlIsAllowed(request)) {
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
    return session != null && session.getAttribute("user") != null;
  }

  private boolean urlIsAllowed(HttpServletRequest request) {
    String url = request.getRequestURL().toString();
    return url.contains("login");
  }

}
