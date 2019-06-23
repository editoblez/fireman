package com.ec.fireman.data.dao;

import com.ec.fireman.data.entities.Role;
import com.ec.fireman.data.entities.RoleTypes;
import lombok.extern.log4j.Log4j2;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import java.util.List;

@Log4j2
@Stateless
public class RoleDao extends GenericDaoImpl<Role> {

  @PostConstruct
  public void init() {
    log.info("Setup fireman app");
    setClazz(Role.class);
    tryCreateRoles();
  }

  private void tryCreateRoles() {
    RoleTypes[] roles = RoleTypes.values();
    List<Role> dbRoles = findAll();
    for (RoleTypes roleType : roles) {
      if (dbRoles.stream().noneMatch(it -> it.getRoleName().equals(roleType.getValue()))) {
        log.debug("Creating a new rol calling " + roleType);
        save(new Role(roleType));
      }
    }
  }

  public Role findRolByName(RoleTypes roleName) {
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
