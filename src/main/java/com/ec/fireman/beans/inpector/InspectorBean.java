package com.ec.fireman.beans.inpector;

import com.ec.fireman.beans.PageNameConstants;
import com.ec.fireman.data.dao.*;
import com.ec.fireman.data.entities.*;
import com.ec.fireman.data.representation.RequirementFileUpload;
import com.ec.fireman.util.MessageUtil;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

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
        MessageUtil.infoFacesMessage("Solicitud", "Permiso " +
                selectedRequest.getLocal().getAddress() +
                " validado correctaente");
        this.refreshRequests();
        this.clearData();
    }


    public List<PermissionRequestFiles> listFiles() {
        List<PermissionRequestFiles> list = permissionRequestFilesDao
                .findPermissionRequestFilesByRequest(selectedRequest.getId());
        log.info("Files length: " + (list != null ? list.size() : 0));
        return list;
    }

    public String mapUrl(PermissionRequest pr) {
        String url = pr != null && pr.getLocal() != null ? pr.getLocal().getMapUrl() : "";
        log.info("Map URL: " + url);
        return url;
    }

    public String redirectToMeetRequest() {
        return PageNameConstants.INSPECTOR_SOLICITUD_PAGE + "?id=" + selectedRequest.getId() + "&faces-redirect=true";
    }

    public String redirectToUploadDocs() {
        return PageNameConstants.DOCUMENTS_UPLOAD + "?id=" + this.selectedRequest.getLocal().getId() + "&faces-redirect=true";
    }

    public String redirectToInspectionPage() {
        return PageNameConstants.INSPECTOR_INSPECTION_PAGE + "?id=" + this.selectedRequest.getId() + "&faces-redirect=true";
    }
}
