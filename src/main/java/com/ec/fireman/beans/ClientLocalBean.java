package com.ec.fireman.beans;

import com.ec.fireman.data.dao.*;
import com.ec.fireman.data.entities.*;
import com.ec.fireman.data.representation.LocalRequest;
import com.ec.fireman.data.representation.RequirementFileUpload;
import com.ec.fireman.util.MessageUtil;
import com.ec.fireman.util.SessionUtils;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    this.selectedItem = new LocalRequest(selectedLocal, selectedRequest);
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
  public void createLocal() {
    selectedLocal.setUserAccount(userAccountDao.findUserByCi(SessionUtils.retrieveLoggedUser().getUserId()));
    selectedLocal.setState(State.ACTIVE);
    selectedLocal.setService(service);
    log.info(selectedLocal.toString());
    PermissionRequest permissionRequest = new PermissionRequest(PermissionRequestStatus.TO_REQUEST, selectedLocal);
    permissionRequestDao.save(permissionRequest);
    this.refreshLocals();
    this.clearData();
  }

  @Transactional
  public void editLocal() {
    selectedLocal.setService(selectedLocal.getService());
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
    selectedRequest.setPermissionRequestStatus(PermissionRequestStatus.REQUESTED);
    selectedRequest.setState(State.ACTIVE);
    permissionRequestDao.update(selectedRequest);
    log.info(selectedRequest.toString());
    MessageUtil.infoFacesMessage("Solicitud", "Permiso de funcionamiento solicitado correctaente");
    this.refreshLocals();
    this.clearData();
  }

  @Transactional
  public void cancelRequest() {
    selectedRequest.setPermissionRequestStatus(PermissionRequestStatus.TO_REQUEST);
    permissionRequestDao.update(selectedRequest);
    MessageUtil.infoFacesMessage("Cancelaci�n", "Permiso de funcionamiento cancelado correctaente");
    this.refreshLocals();
    this.clearData();
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
        PermissionRequest permissionRequest = permissionRequestDao.findPermissionRequestByLocal(selectedLocal.getId());
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
    this.refreshLocals();
    this.clearData();
  }

  public List<Service> listServices() {
    return serviceDao.findAll();
  }

  public List<RequirementFileUpload> listRequirements() {
    // TODO: LIST ACTIVE REQUIREMENTS BY ROLE (DAO)
    List<Requirement> requirements = requirementDao.findAll().stream().filter(it -> it.getRole().getRoleName() == RoleTypes.CLIENT).collect(Collectors.toList());
    files = new ArrayList<RequirementFileUpload>();
    if (requirements != null && !requirements.isEmpty()) {
      for (Requirement req : requirements) {
        files.add(new RequirementFileUpload(req.getId(), req.getName()));
      }
    }
    return files;
  }

}
