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

import org.apache.commons.io.IOUtils;

import com.ec.fireman.data.dao.LocalDao;
import com.ec.fireman.data.dao.PermissionRequestDao;
import com.ec.fireman.data.dao.PermissionRequestFilesDao;
import com.ec.fireman.data.dao.RequirementDao;
import com.ec.fireman.data.dao.ServiceDao;
import com.ec.fireman.data.dao.UserAccountDao;
import com.ec.fireman.data.entities.Local;
import com.ec.fireman.data.entities.PermissionRequest;
import com.ec.fireman.data.entities.PermissionRequestFiles;
import com.ec.fireman.data.entities.PermissionRequestStatus;
import com.ec.fireman.data.entities.Requirement;
import com.ec.fireman.data.entities.Service;
import com.ec.fireman.data.entities.State;
import com.ec.fireman.data.representation.LocalRequest;
import com.ec.fireman.data.representation.RequirementFileUpload;
import com.ec.fireman.util.SessionUtils;

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
  private UserAccountDao userAccountDao;
  @Inject
  private RequirementDao requirementDao;
  @Inject
  private PermissionRequestDao permissionRequestDao;
  @Inject
  private PermissionRequestFilesDao permissionRequestFilesDao;

  private List<LocalRequest> locals;
  private Local selectedLocal;
  private Service service;
  private List<RequirementFileUpload> files;
  private PermissionRequest selectedRequest;

  @PostConstruct
  public void init() {
    this.refreshLocals();
    selectedLocal = new Local();
    selectedRequest = new PermissionRequest();
  }

  public void refreshLocals() {
    List<Local> localList = localDao.findLocalByUser(SessionUtils.retrieveLoggedUser().getUserId());
    locals = new ArrayList<LocalRequest>();
    for (Local local : localList) {
      PermissionRequest pr = permissionRequestDao.findPermissionRequestByLocal(local.getId());
      locals.add(new LocalRequest(local, pr));
    }
    log.info("Locals length: " + (locals != null ? locals.size() : 0));
  }

  public void clearData() {
    selectedLocal = new Local();
    selectedRequest = new PermissionRequest();
  }

  @Transactional
  public void createLocal() {
    selectedLocal.setUserAccount(userAccountDao.findUserByCi(SessionUtils.retrieveLoggedUser().getUserId()));
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
    request.setPermissionRequestStatus(PermissionRequestStatus.SUBMITTED);
    request.setState(State.ACTIVE);
    permissionRequestDao.save(request);
    log.info(request.toString());
    this.sendFacesMessage("Solicitud", "Permiso de funcionamiento solicitado correctaente");
    this.refreshLocals();
    this.clearData();
  }

  @Transactional
  public void cancelRequest() {
    selectedRequest.setPermissionRequestStatus(PermissionRequestStatus.CLOSED);
    permissionRequestDao.update(selectedRequest);
    this.sendFacesMessage("Cancelaci�n", "Permiso de funcionamiento cancelado correctaente");
    this.refreshLocals();
    this.clearData();
  }

  public void upload() {
    for (RequirementFileUpload requirementFileUpload : files) {
      log.info(requirementFileUpload.toString());
      if (requirementFileUpload.getFile() != null) {
        byte[] bytes = null;
        try {
          bytes = IOUtils.toByteArray(requirementFileUpload.getFile().getInputstream());
        } catch (IOException e) {
          log.error(e.getMessage());
        }
        PermissionRequest permissionRequest = permissionRequestDao.findPermissionRequestByLocal(selectedLocal.getId());
        PermissionRequestFiles prf = new PermissionRequestFiles();
        prf.setPermissionRequest(permissionRequest);
        prf.setRequirement(requirementDao.findById(requirementFileUpload.getRequirementId()));
        prf.setState(State.ACTIVE);
        prf.setData(bytes);
        prf.setFileName(requirementFileUpload.getFile().getFileName());
        permissionRequestFilesDao.save(prf);

        this.sendFacesMessage("Succesful", requirementFileUpload.getFile().getFileName() + " is uploaded.");
      }
    }
  }

  public List<Service> listServices() {
    return serviceDao.findAll();
  }

  public List<RequirementFileUpload> listRequirements() {
    // TODO: LIST ACTIVE REQUIREMENTS BY ROLE (DAO)
    List<Requirement> requirements = requirementDao.findAll();
    files = new ArrayList<RequirementFileUpload>();
    if (requirements != null && !requirements.isEmpty()) {
      for (Requirement req : requirements) {
        files.add(new RequirementFileUpload(req.getId(), req.getName()));
      }
    }
    return files;
  }

  private void sendFacesMessage(String title, String description) {
    FacesMessage message = new FacesMessage(title, description);
    FacesContext.getCurrentInstance().addMessage(null, message);
  }

}
