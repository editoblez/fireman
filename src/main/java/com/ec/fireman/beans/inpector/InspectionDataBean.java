package com.ec.fireman.beans.inpector;

import com.ec.fireman.beans.PageNameConstants;
import com.ec.fireman.data.dao.InspectionFireExtinguisherDao;
import com.ec.fireman.data.dao.InspectionHeaderDao;
import com.ec.fireman.data.dao.PermissionRequestDao;
import com.ec.fireman.data.entities.InspectionFireExtinguisher;
import com.ec.fireman.data.entities.InspectionHeader;
import com.ec.fireman.data.entities.PermissionRequest;
import com.ec.fireman.data.entities.State;
import com.github.adminfaces.template.util.Assert;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Data
@Log4j2
@Named
@ViewScoped
public class InspectionDataBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private InspectionHeaderDao inspectionHeaderDao;
    @Inject
    private InspectionFireExtinguisherDao inspectionFireExtinguisherDao;
    @Inject
    private PermissionRequestDao permissionRequestDao;

    private InspectionHeader inspectionHeader;
    private List<InspectionFireExtinguisher> extinguishers;

    Long id;
    private PermissionRequest selectedRequest;

    public void init() {
        extinguishers = new ArrayList<>();
        inspectionHeader = null;
        if (Assert.has(id) && id != null) {
            selectedRequest = permissionRequestDao.findById(id);
            this.fillInspectionData(selectedRequest);
            inspectionHeader = inspectionHeaderDao.findByPermissionRequest(selectedRequest);
        }
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
    }

    public void addExtinguisher() {
        extinguishers.add(new InspectionFireExtinguisher());
    }

    private void fillInspectionData(PermissionRequest pr) {
        log.info(pr.toString());
        inspectionHeader = inspectionHeaderDao.findByPermissionRequest(pr);
        log.info(inspectionHeader.toString());
        extinguishers = inspectionFireExtinguisherDao.findInspectionFireExtinguisherByHeader(inspectionHeader.getId());
    }


    public String redirectTo() {
        return PageNameConstants.INSPECTOR_PAGE + "?faces-redirect=true";
    }
}
