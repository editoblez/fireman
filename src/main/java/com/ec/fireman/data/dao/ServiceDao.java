package com.ec.fireman.data.dao;

import com.ec.fireman.data.entities.Service;
import lombok.extern.log4j.Log4j2;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import java.util.List;

@Log4j2
@Stateless
public class ServiceDao extends GenericDaoImpl<Service> {

  @PostConstruct
  public void init() {
    log.info("ServiceDao was successfully created");
    setClazz(Service.class);
  }

  public Service findServiceByName(String serviceName) {
    Service service = null;
    try {
      service = (Service) entityManager.createNamedQuery("findServiceByName")
          .setParameter("name", serviceName)
          .getSingleResult();
    } catch (Exception ex) {
      log.error("Error to execute findServiceByName", ex);
    }
    return service;
  }

  public List<Service> findServicesByName(String serviceName) {
    List<Service> services = null;
    try {
      services = (List<Service>) entityManager.createNamedQuery("findServicesByName")
              .setParameter("name", "%" + serviceName + "%")
              .getResultList();
    }catch (Exception ex) {
      log.error("Error to execute findServicesByName", ex);
    }
    return services;
  }

}
