package com.ec.fireman.beans.inpector;

import com.ec.fireman.beans.PageNameConstants;
import com.ec.fireman.data.dao.*;
import com.ec.fireman.data.entities.InspectionHeader;
import com.ec.fireman.data.entities.PermissionRequest;
import com.ec.fireman.data.entities.PermissionRequestStatus;
import com.ec.fireman.util.MessageUtil;
import com.ec.fireman.util.SessionUtils;
import com.github.adminfaces.template.util.Assert;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;

@Data
@Log4j2
@Named
@ViewScoped
public class SolicitudBean implements Serializable {
    private static final long serialVersionUID = 1L;

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

    Long id;
    private PermissionRequest permissionRequest;

    public void init() {
        if (Assert.has(id) && id != null) {
            permissionRequest = permissionRequestDao.findById(id);
        }
    }

    public String redirectTo() {
        return PageNameConstants.INSPECTOR_PAGE + "?faces-redirect=true";
    }


    @Transactional
    public void startInspection() {
        localDao.update(permissionRequest.getLocal());
        permissionRequest.setPermissionRequestStatus(PermissionRequestStatus.IN_PROGRESS);
        permissionRequest.setInspector(userAccountDao.findUserByCi(SessionUtils.retrieveLoggedUser().getUserId()));
        permissionRequestDao.update(permissionRequest);
        MessageUtil.addDetailMessage("Inspección asignada correctamente");
        InspectionHeader inspectionHeader = new InspectionHeader(permissionRequest);
        inspectionHeaderDao.save(inspectionHeader);
    }
}
