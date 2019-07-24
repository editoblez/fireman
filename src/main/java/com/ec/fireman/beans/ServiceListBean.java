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
  private String nombre;

  @PostConstruct
  public void init() {
    this.refreshServices();
    this.selectedService = new Service();
  }

  public void refreshServices() {
    services = serviceDao.findAll();
    log.info("Services length: " + services != null ? services.size() : 0);
    selectedService = new Service();
  }

  public void findByName(String nombre) {
    services = null;
    if (nombre != null && !nombre.isEmpty()) {
      services = serviceDao.findServicesByName(nombre);
    } else {
      services = serviceDao.findAll();
    }
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

  public void save() {
    if ((Long)this.selectedService.getId() == null) {
      this.createService();
    } else {
      this.refreshServices();
    }
  }

  public void clear() {
    this.selectedService = new Service();
  }


  public boolean isNew() {
    return this.selectedService == null ||
            (((Long)this.selectedService.getId() == null || this.selectedService.getId() == 0) &&
                    this.selectedService.getName() == null);
  }

}
