package com.ec.fireman.data.dao;

import com.ec.fireman.data.entities.Role;
import lombok.extern.log4j.Log4j2;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

@Log4j2
@Stateless
public class RoleDao extends GenericDaoImpl<Role> {

  @PostConstruct
  public void init() {
    log.info("Setup fireman app");
    setClazz(Role.class);
  }

  public Role findRolByName(String roleName) {
    Role role = null;
    try {
      role = (Role) entityManager.createNamedQuery("findRoleByName")
          .setParameter("roleName", roleName)
          .getSingleResult();
    } catch (Exception ex) {

      log.error("Error to execute findRoleByName", ex);
    }
    return role;
  }
}
