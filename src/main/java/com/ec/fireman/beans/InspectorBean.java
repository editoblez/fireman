package com.ec.fireman.beans;

import com.ec.fireman.data.dao.LocalDao;
import com.ec.fireman.data.dao.PermissionRequestDao;
import com.ec.fireman.data.dao.PermissionRequestFilesDao;
import com.ec.fireman.data.dao.RequirementDao;
import com.ec.fireman.data.entities.MimeTypes;
import com.ec.fireman.data.entities.PermissionRequest;
import com.ec.fireman.data.entities.PermissionRequestFiles;
import com.ec.fireman.data.entities.PermissionRequestStatus;
import com.ec.fireman.data.representation.RequirementFileUpload;
import com.ec.fireman.util.MessageUtil;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

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

  private List<PermissionRequest> requests;
  private List<RequirementFileUpload> files;
  private PermissionRequest selectedRequest;
  private PermissionRequestFiles selectedPRF;

  @PostConstruct
  public void init() {
    this.refreshRequests();
    selectedRequest = new PermissionRequest();
  }

  public void refreshRequests() {
    requests = permissionRequestDao.findPermissionRequestByPermissionRequestStatus(PermissionRequestStatus.REQUESTED);
    log.info("Requests length: " + (requests != null ? requests.size() : 0));
  }

  public void clearData() {
    selectedRequest = new PermissionRequest();
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

  public StreamedContent download(PermissionRequestFiles prf) {
    InputStream stream = new ByteArrayInputStream(prf.getData());
    String suffix = prf.getFileName().substring(prf.getFileName().lastIndexOf("."));
    return new DefaultStreamedContent(stream, MimeTypes.findBySuffix(suffix).getMimeType(), prf.getFileName());
  }

  public List<PermissionRequestFiles> listFiles() {
    List<PermissionRequestFiles> list = permissionRequestFilesDao
        .findPermissionRequestFilesByRequest(selectedRequest.getId());
    log.info("Files length: " + (list != null ? list.size() : 0));
    return list;
  }

}
