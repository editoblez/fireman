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
    extinguishers.add(new InspectionFireExtinguisher());
    inspectionHeader = new InspectionHeader();
  }

  public void refreshRequests() {
    requests = permissionRequestDao.findPermissionRequestByPermissionRequestStatus(PermissionRequestStatus.REQUESTED);
    requests.addAll(permissionRequestDao.findAllInProgressByLoggedUser());
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

  @Transactional
  public void startInspection() {
    localDao.update(selectedRequest.getLocal());
    selectedRequest.setPermissionRequestStatus(PermissionRequestStatus.IN_PROGRESS);
    selectedRequest.setInspector(userAccountDao.findUserByCi(SessionUtils.retrieveLoggedUser().getUserId()));
    permissionRequestDao.update(selectedRequest);
    MessageUtil.infoFacesMessage("Solicitud", "Inspecci�n asignada correctaente");
    inspectionHeader = new InspectionHeader();
    this.refreshRequests();
    this.clearData();
  }

  public void addExtinguisher() {
    extinguishers.add(new InspectionFireExtinguisher());
  }

  @Transactional
  public void saveInspection() {
    if (inspectionHeader.getId() > 0) {
      inspectionHeaderDao.update(inspectionHeader);
    } else {
      inspectionHeader.setPermissionRequest(selectedRequest);
      inspectionHeader.setState(State.ACTIVE);
      inspectionHeaderDao.save(inspectionHeader);

    }
    for (InspectionFireExtinguisher item : extinguishers) {
      inspectionFireExtinguisherDao.save(item);
    }
  }

  public List<RequirementFileUpload> listRequirements() {
    // TODO: LIST ACTIVE REQUIREMENTS BY ROLE (DAO)
    List<Requirement> requirements = requirementDao.findAll().stream()
        .filter(it -> it.getRole().getRoleName() == RoleTypes.INSPECTOR).collect(Collectors.toList());
    files = new ArrayList<RequirementFileUpload>();
    if (requirements != null && !requirements.isEmpty()) {
      for (Requirement req : requirements) {
        files.add(new RequirementFileUpload(req.getId(), req.getName()));
      }
    }
    return files;
  }

  public void upload() {
    // TODO: verificar que el archivo del requerimiento que se va a subir
    // no esté cargado, en caso de que ya lo esté, se debe eliminar y subirlo
    // nuevamente
    for (RequirementFileUpload requirementFileUpload : files) {
      log.info(requirementFileUpload.toString());
      if (requirementFileUpload.getFile() != null) {
        String suffix = requirementFileUpload.getFile().getFileName()
            .substring(requirementFileUpload.getFile().getFileName().lastIndexOf("."));
        if (MimeTypes.findBySuffix(suffix) == null) {
          MessageUtil.errorFacesMessage("Carga Archivo",
              "La extensión del archivo " + requirementFileUpload.getFile().getFileName() + " no es permitida.");
          continue;
        }
        byte[] bytes = null;
        try {
          bytes = IOUtils.toByteArray(requirementFileUpload.getFile().getInputstream());
        } catch (IOException e) {
          log.error(e.getMessage());
        }
        PermissionRequest permissionRequest = permissionRequestDao
            .findPermissionRequestByLocal(selectedRequest.getLocal().getId());
        PermissionRequestFiles prf = new PermissionRequestFiles();
        prf.setPermissionRequest(permissionRequest);
        prf.setRequirement(requirementDao.findById(requirementFileUpload.getRequirementId()));
        prf.setState(State.ACTIVE);
        prf.setData(bytes);
        prf.setFileName(requirementFileUpload.getFile().getFileName());
        permissionRequestFilesDao.save(prf);

        MessageUtil.infoFacesMessage("Carga Archivo",
            requirementFileUpload.getFile().getFileName() + " subido correctamente.");
      }
    }
    this.refreshRequests();
    this.clearData();
  }
  
  public String mapUrl(PermissionRequest pr) {
    String url = pr != null && pr.getLocal() != null ? pr.getLocal().getMapUrl() : "";
    log.info("Map URL: " + url);
    return url;
  }

}
