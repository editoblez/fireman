package com.ec.fireman.beans;

import com.ec.fireman.data.dao.*;
import com.ec.fireman.data.entities.*;
import com.ec.fireman.data.representation.RequirementFileUpload;
import com.ec.fireman.util.MessageUtil;
import com.ec.fireman.util.SessionUtils;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Log4j2
@Named
@ViewScoped
public class InspectorBean implements Serializable {

  private static final long serialVersionUID = 8632563163040959753L;

  @Inject
  private LocalDao localDao;
  @Inject
  private RequirementDao requirementDao;
  @Inject
  private PermissionRequestDao permissionRequestDao;
  @Inject
  private PermissionRequestFilesDao permissionRequestFilesDao;
  @Inject
  private InspectionHeaderDao inspectionHeaderDao;
  @Inject
  private InspectionFireExtinguisherDao inspectionFireExtinguisherDao;
  @Inject
  private UserAccountDao userAccountDao;

  private List<PermissionRequest> requests;
  private List<RequirementFileUpload> files;
  private PermissionRequest selectedRequest;
  private PermissionRequestFiles selectedPRF;
  private InspectionHeader inspectionHeader;
  private List<InspectionFireExtinguisher> extinguishers;

  @PostConstruct
  public void init() {
    this.refreshRequests();
    selectedRequest = new PermissionRequest();
    extinguishers = new ArrayList<>();
  }

  public void refreshRequests() {
    requests = permissionRequestDao.findPermissionRequestByPermissionRequestStatus(PermissionRequestStatus.REQUESTED);
    requests.addAll(permissionRequestDao.findAllInProgressByLoggedUser());
    requests.addAll(permissionRequestDao.findAllPermissionRequestToExpire());
    requests.addAll(permissionRequestDao.findAllPermissionRequestExpired());
    log.info("Requests length: " + (requests != null ? requests.size() : 0));
  }

  public void clearData() {
    selectedRequest = new PermissionRequest();
    extinguishers = new ArrayList<>();
    inspectionHeader = null;
  }

  @Transactional
  public void editRequest() {
    selectedRequest.setPermissionRequestStatus(PermissionRequestStatus.TO_PAIED);
    permissionRequestDao.update(selectedRequest);
    log.info(selectedRequest.toString());
    MessageUtil.infoFacesMessage("Solicitud", "Permiso validado correctaente");
    this.refreshRequests();
    this.clearData();
  }



  public List<PermissionRequestFiles> listFiles() {
    List<PermissionRequestFiles> list = permissionRequestFilesDao
        .findPermissionRequestFilesByRequest(selectedRequest.getId());
    log.info("Files length: " + (list != null ? list.size() : 0));
    return list;
  }

  @Transactional
  public void startInspection() {
    localDao.update(selectedRequest.getLocal());
    selectedRequest.setPermissionRequestStatus(PermissionRequestStatus.IN_PROGRESS);
    selectedRequest.setInspector(userAccountDao.findUserByCi(SessionUtils.retrieveLoggedUser().getUserId()));
    permissionRequestDao.update(selectedRequest);
    MessageUtil.infoFacesMessage("Solicitud", "Inspección asignada correctamente");
    inspectionHeader = new InspectionHeader(selectedRequest);
    inspectionHeaderDao.save(inspectionHeader);
    this.refreshRequests();
    this.clearData();
  }

  public void addExtinguisher() {
    extinguishers.add(new InspectionFireExtinguisher());
  }

  public void fillInspectionData(PermissionRequest pr) {
    log.info(pr.toString());
    inspectionHeader = inspectionHeaderDao.findByPermissionRequest(pr);
    log.info(inspectionHeader.toString());
    extinguishers = inspectionFireExtinguisherDao.findInspectionFireExtinguisherByHeader(inspectionHeader.getId());
  }

  @Transactional
  public void saveInspection() {
    if (inspectionHeader.getId() <= 0) {
      inspectionHeader.setPermissionRequest(selectedRequest);
      inspectionHeader.setState(State.ACTIVE);
    }
    inspectionHeader.setLastUpdate(Calendar.getInstance().getTimeInMillis());
    inspectionHeaderDao.update(inspectionHeader);

    inspectionFireExtinguisherDao.removeInspectionFireExtinguisherByHeader(inspectionHeader.getId());
    for (InspectionFireExtinguisher item : extinguishers) {
      item.setInspectionHeader(inspectionHeader);
      inspectionFireExtinguisherDao.update(item);
    }
    this.clearData();
  }

  public String mapUrl(PermissionRequest pr) {
    String url = pr != null && pr.getLocal() != null ? pr.getLocal().getMapUrl() : "";
    log.info("Map URL: " + url);
    return url;
  }
}
