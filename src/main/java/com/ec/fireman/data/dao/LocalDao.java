package com.ec.fireman.data.dao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

import com.ec.fireman.data.entities.Local;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Stateless
public class LocalDao extends GenericDaoImpl<Local> {

  @PostConstruct
  public void init() {
    log.info("LocalDao was successfully created");
    setClazz(Local.class);
  }

  public List<Local> findLocalByUser(String ci) {
    try {
      return entityManager.createNamedQuery("findLocalByUser").setParameter("ci", ci).getResultList();
    } catch (Exception ex) {
      log.error("Error to execute findLocalByUser", ex);
    }
    return null;
  }

}
