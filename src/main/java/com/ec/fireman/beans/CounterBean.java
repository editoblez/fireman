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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Log4j2
@Named
@ViewScoped
public class CounterBean implements Serializable {

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
  private PermissionIssueDao permissionIssueDao;
  @Inject
  private InspectionHeaderDao inspectionHeaderDao;
  @Inject
  private UserAccountDao userAccountDao;

  private List<PermissionRequest> requests;
  private List<RequirementFileUpload> files;
  private PermissionRequest selectedRequest;
  private BigDecimal price;

  @PostConstruct
  public void init() {
    this.refreshRequests();
    selectedRequest = new PermissionRequest();
  }

  public void refreshRequests() {
    requests = permissionRequestDao.findPermissionRequestByPermissionRequestStatus(PermissionRequestStatus.TO_PAIED);
    log.info("Permissions length: " + (requests != null ? requests.size() : 0));
  }

  public void clearData() {
    selectedRequest = new PermissionRequest();
  }

  @Transactional
  public void editRequest() {
    selectedRequest.setPermissionRequestStatus(PermissionRequestStatus.PERMISSION_ISSUED);
    selectedRequest.setEconomic(userAccountDao.findUserByCi(SessionUtils.retrieveLoggedUser().getUserId()));
    permissionRequestDao.update(selectedRequest);

    createNewPermissionIssue();

    log.info(selectedRequest.toString());
    MessageUtil.infoFacesMessage("Solicitud", "Permiso de funcionamiento emitido");
    this.refreshRequests();
    this.clearData();
  }

  private void createNewPermissionIssue() {
    permissionIssueDao.inactivePreviousPermissionIssued(selectedRequest);
    permissionIssueDao.save(new PermissionIssue(price, inspectionHeaderDao.findByPermissionRequest(selectedRequest)));
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
//        PermissionRequest permissionRequest = permissionRequestDao.findPermissionRequestByLocal(selectedLocal.getId());
        PermissionRequestFiles prf = new PermissionRequestFiles();
//        prf.setPermissionRequest(permissionRequest);
        prf.setRequirement(requirementDao.findById(requirementFileUpload.getRequirementId()));
        prf.setState(State.ACTIVE);
        prf.setData(bytes);
        prf.setFileName(requirementFileUpload.getFile().getFileName());
        permissionRequestFilesDao.save(prf);

        MessageUtil.infoFacesMessage("Succesful", requirementFileUpload.getFile().getFileName() + " is uploaded.");
      }
    }
  }

  @Transactional
  public void cancelRequest() {
    selectedRequest.setPermissionRequestStatus(PermissionRequestStatus.REJECTED);
    permissionRequestDao.update(selectedRequest);
    MessageUtil.infoFacesMessage("Cancelaci�n", "Permiso de funcionamiento cancelado correctaente");
    this.refreshRequests();
    this.clearData();
  }

  public List<RequirementFileUpload> listRequirements() {
    // TODO: LIST ACTIVE REQUIREMENTS BY ROLE (DAO)
    List<Requirement> requirements = requirementDao.findAll();
    files = new ArrayList<>();
    if (requirements != null && !requirements.isEmpty()) {
      for (Requirement req : requirements) {
        files.add(new RequirementFileUpload(req.getId(), req.getName()));
      }
    }
    return files;
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
