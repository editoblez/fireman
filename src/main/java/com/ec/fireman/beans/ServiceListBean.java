package com.ec.fireman.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import com.ec.fireman.data.dao.ServiceDao;
import com.ec.fireman.data.entities.Service;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Data
@Log4j2
@Named
@SessionScoped
public class ServiceListBean implements Serializable {

  private static final long serialVersionUID = -5468228478359216158L;

  @Inject
  private ServiceDao serviceDao;

  private List<Service> services;
  private Service selectedService;

  @PostConstruct
  public void init() {
    this.refreshServices();
    setSelectedService(new Service());
  }

  public void refreshServices() {
    services = serviceDao.findAll();
    log.info("Services length: " + services != null ? services.size() : 0);
  }

  @Transactional
  public void createService() {
    serviceDao.save(selectedService);
    this.refreshServices();
    setSelectedService(new Service());
  }

  @Transactional
  public void editService() {
    serviceDao.update(selectedService);
    this.refreshServices();
    setSelectedService(new Service());
  }

}
