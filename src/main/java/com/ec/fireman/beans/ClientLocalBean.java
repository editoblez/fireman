package com.ec.fireman.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import com.ec.fireman.data.dao.LocalDao;
import com.ec.fireman.data.dao.PermissionRequestDao;
import com.ec.fireman.data.dao.PermissionRequestFilesDao;
import com.ec.fireman.data.dao.RequirementDao;
import com.ec.fireman.data.dao.ServiceDao;
import com.ec.fireman.data.entities.Local;
import com.ec.fireman.data.entities.PermissionRequest;
import com.ec.fireman.data.entities.PermissionRequestStatus;
import com.ec.fireman.data.entities.Requirement;
import com.ec.fireman.data.entities.Service;
import com.ec.fireman.data.entities.State;
import com.ec.fireman.data.representation.RequirementFileUpload;
import com.ec.fireman.util.Base64Util;

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
  @Inject
  private RequirementDao requirementDao;
  @Inject
  private PermissionRequestDao permissionRequestDao;
  @Inject
  private PermissionRequestFilesDao permissionRequestFilesDao;

  private List<Local> locals;
  private Local selectedLocal;
  private Service service;
  private List<RequirementFileUpload> files;
  private String mapPosition;

  @PostConstruct
  public void init() {
    this.refreshLocals();
    selectedLocal = new Local();
    mapPosition = "-0.176116, -78.485530";
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
    // TODO: CHANGE ENTITY CLIENT FOR USER
    // selectedLocal.setClient(client);
    selectedLocal.setState(State.ACTIVE);
    selectedLocal.setService(service);
    log.info(selectedLocal.toString());
    localDao.save(selectedLocal);
    this.refreshLocals();
    this.clearData();
  }

  @Transactional
  public void editLocal() {
    selectedLocal.setService(service);
    log.info(selectedLocal.toString());
    localDao.update(selectedLocal);
    this.refreshLocals();
    this.clearData();
  }

  @Transactional
  public void deleteLocal() {
    log.info(selectedLocal.toString());
    selectedLocal.setState(State.INACTIVE);
    localDao.update(selectedLocal);
    this.refreshLocals();
    this.clearData();
  }

  @Transactional
  public void localRequest() {
    PermissionRequest request = new PermissionRequest();
    request.setLocal(localDao.findById(selectedLocal.getId()));
    request.setPermissionRequestStatus(PermissionRequestStatus.IN_PROGRESS);
    request.setState(State.ACTIVE);
    permissionRequestDao.save(request);
    log.info(request.toString());
  }

  public List<Service> listServices() {
    return serviceDao.findAll();
  }

  public List<RequirementFileUpload> listRequirements() {
    List<Requirement> requirements = requirementDao.findAll();
    files = new ArrayList<RequirementFileUpload>();
    if (requirements != null && !requirements.isEmpty()) {
      for (Requirement req : requirements) {
        files.add(new RequirementFileUpload(req.getId(), req.getName()));
      }
    }
    return files;
  }

  public void upload() {
    for (RequirementFileUpload requirementFileUpload : files) {
      log.info(requirementFileUpload.toString());
      if (requirementFileUpload.getFile() != null) {

        // TODO: SAVE INTO DATABASE (PermissionRequestFiles)
        try {
          log.info(Base64Util.inputStreamToBase64(requirementFileUpload.getFile().getInputstream()));
        } catch (IOException e) {
          log.error(e.getMessage());
        }

        FacesMessage message = new FacesMessage("Succesful",
            requirementFileUpload.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
      }
    }
  }

}
