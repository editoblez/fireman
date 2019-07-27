package com.ec.fireman.beans.clientelocal;

import com.ec.fireman.beans.PageNameConstants;
import com.ec.fireman.beans.PageRedirectConstants;
import com.ec.fireman.data.dao.*;
import com.ec.fireman.data.entities.*;
import com.ec.fireman.data.representation.LocalRequest;
import com.ec.fireman.data.representation.RequirementFileUpload;
import com.ec.fireman.util.MessageUtil;
import com.ec.fireman.util.SessionUtils;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.omnifaces.util.Faces;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Log4j2
@Named
@ViewScoped
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
  private LocalRequest selectedItem;
  private Local selectedLocal;
  private Service service;
  private List<RequirementFileUpload> files;
  private PermissionRequest selectedRequest;

  @PostConstruct
  public void init() {
    this.refreshLocals();
    selectedLocal = new Local();
    selectedRequest = new PermissionRequest();
    this.selectedItem = null;
    Faces.setSessionAttribute(PageRedirectConstants.REFERER, null);
  }

  public void refreshLocals() {
    List<Local> localList = localDao.findLocalByUser(SessionUtils.retrieveLoggedUser().getUserId());
    locals = new ArrayList<>();
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
  public void deleteLocal(Long id) {
    selectedLocal = localDao.findById(id);
    log.info(selectedLocal.toString());
    selectedLocal.setState(State.INACTIVE);
    localDao.update(selectedLocal);
    this.refreshLocals();
    this.clearData();
  }

  @Transactional
  public void localRequest() {
    selectedRequest.setPermissionRequestStatus(PermissionRequestStatus.REQUESTED);
    selectedRequest.setState(State.ACTIVE);
    permissionRequestDao.update(selectedRequest);
    log.info(selectedRequest.toString());
    MessageUtil.addDetailMessage("Permiso de funcionamiento solicitado correctamente");
    this.refreshLocals();
    this.clearData();
  }

  @Transactional
  public void cancelRequest() {
    selectedRequest.setPermissionRequestStatus(PermissionRequestStatus.TO_REQUEST);
    permissionRequestDao.update(selectedRequest);
    MessageUtil.addDetailMessage("Permiso de funcionamiento cancelado correctamente");
    this.refreshLocals();
    this.clearData();
  }

  public List<Service> listServices() {
    return serviceDao.findAll();
  }

  public String redirectToLocalForm() {
    return PageNameConstants.LOCAL_CLIENT_FORM_PAGE + "?id=" + this.selectedItem.getLocal().getId() + "&faces-redirect=true";
  }

  public String redirectToUploadDocs() {
    return PageNameConstants.DOCUMENTS_UPLOAD + "?id=" + this.selectedItem.getLocal().getId() + "&faces-redirect=true";
  }
}
