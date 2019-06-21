package com.ec.fireman.data.dao;

import com.ec.fireman.data.entities.Requirement;
import lombok.extern.log4j.Log4j2;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

@Log4j2
@Stateless
public class RequirementDao extends GenericDaoImpl<Requirement> {

  @PostConstruct
  public void init() {
    log.info("ServiceDao was successfully created");
    setClazz(Requirement.class);
  }

  public Requirement findServiceByName(String requirementName) {
    Requirement requirement = null;
    try {
      requirement = (Requirement) entityManager.createNamedQuery("findRequirementByName")
          .setParameter("name", requirementName)
          .getSingleResult();
    } catch (Exception ex) {
      log.error("Error to execute findRequirementByName", ex);
    }
    return requirement;
  }

}
