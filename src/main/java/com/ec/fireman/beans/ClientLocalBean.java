package com.ec.fireman.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import com.ec.fireman.data.dao.LocalDao;
import com.ec.fireman.data.dao.ServiceDao;
import com.ec.fireman.data.entities.Local;
import com.ec.fireman.data.entities.Service;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Data
@Log4j2
@Named
@SessionScoped
public class ClientLocalBean implements Serializable {

  private static final long serialVersionUID = -5093549162702207471L;

  @Inject
  private LocalDao localDao;
  @Inject
  private ServiceDao serviceDao;

  private List<Local> locals;
  private Local selectedLocal;
  private Service service;

  @PostConstruct
  public void init() {
    this.refreshLocals();
  }

  public void refreshLocals() {
    locals = localDao.findAll();
    log.info("Locals length: " + (locals != null ? locals.size() : 0));
  }

  public void clearData() {
    selectedLocal = new Local();
  }

  @Transactional
  public void createLocal() {
    log.info(selectedLocal.toString());
    selectedLocal.setService(service);
    localDao.save(selectedLocal);
    this.refreshLocals();
    this.clearData();
  }

  @Transactional
  public void editLocal() {
    log.info(selectedLocal.toString());
    selectedLocal.setService(service);
    log.info(service.toString());
    localDao.update(selectedLocal);
    this.refreshLocals();
    this.clearData();
  }

  @Transactional
  public void deleteLocal() {
    log.info(selectedLocal.getId());
    log.info(selectedLocal.toString());
    localDao.removeById(selectedLocal.getId());
    this.refreshLocals();
    this.clearData();
  }

  public List<Service> listServices() {
    return serviceDao.findAll();
  }

}
