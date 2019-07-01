package com.ec.fireman.beans;

import com.ec.fireman.data.dao.ServiceDao;
import com.ec.fireman.data.entities.Service;
import com.ec.fireman.data.entities.State;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

@Data
@Log4j2
@Named
@ViewScoped
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
    selectedService = new Service();
  }

  @Transactional
  public void createService() {
    selectedService.setState(State.ACTIVE);
    serviceDao.save(selectedService);
    this.refreshServices();
  }

  @Transactional
  public void editService() {
    serviceDao.update(selectedService);
    this.refreshServices();
  }

}
