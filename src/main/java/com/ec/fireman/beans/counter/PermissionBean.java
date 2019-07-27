package com.ec.fireman.beans.counter;

import com.ec.fireman.beans.PageNameConstants;
import com.ec.fireman.data.dao.*;
import com.ec.fireman.data.entities.PermissionIssue;
import com.ec.fireman.data.entities.PermissionRequest;
import com.ec.fireman.data.entities.PermissionRequestStatus;
import com.ec.fireman.util.MessageUtil;
import com.ec.fireman.util.SessionUtils;
import com.github.adminfaces.template.util.Assert;
import javassist.bytecode.stackmap.BasicBlock;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

@Data
@Log4j2
@Named
@ViewScoped
public class PermissionBean implements Serializable {
    private static final long serialVersionUID = 1L;


    @Inject
    private UserAccountDao userAccountDao;
    @Inject
    private PermissionRequestDao permissionRequestDao;
    @Inject
    private PermissionIssueDao permissionIssueDao;
    @Inject
    private ParameterDao parameterDao;
    @Inject
    private InspectionHeaderDao inspectionHeaderDao;

    Long id;
    private PermissionRequest selectedRequest;
    private BigDecimal price;

    public void init() {
        if (Assert.has(id) && id != null) {
            selectedRequest = permissionRequestDao.findById(id);
        }
    }

    @Transactional
    public void editRequest() {
        selectedRequest.setPermissionRequestStatus(PermissionRequestStatus.PERMISSION_ISSUED);
        selectedRequest.setEconomic(userAccountDao.findUserByCi(SessionUtils.retrieveLoggedUser().getUserId()));
        permissionRequestDao.update(selectedRequest);

        createNewPermissionIssue();
        log.info(selectedRequest.toString());
        MessageUtil.infoFacesMessage("Solicitud", "Permiso de funcionamiento emitido");
    }

    @Transactional
    private void createNewPermissionIssue() {
        try {
            Calendar timeCalendar = Calendar.getInstance();
            Calendar closeToExpireCalendar = Calendar.getInstance();
            Calendar expiredCalendar = Calendar.getInstance();
            permissionIssueDao.inactivePreviousPermissionIssued(selectedRequest);
            long time = timeCalendar.getTimeInMillis();
            closeToExpireCalendar.add(Calendar.MONTH, parameterDao.findMonthQuantityCloseToAddToExpire());
            long closeToExpire = closeToExpireCalendar.getTimeInMillis();
            expiredCalendar.add(Calendar.MONTH, parameterDao.findMonthQuantityToAddToExpire());
            long expire = expiredCalendar.getTimeInMillis();
            permissionIssueDao.save(new PermissionIssue(price, inspectionHeaderDao.findByPermissionRequest(selectedRequest), time, closeToExpire, expire));
        } catch (Exception ex) {
            MessageUtil.errorFacesMessage("Error", ex.getMessage());
        }

    }

    public String redirectTo() {
        return PageNameConstants.COUNTER_PAGE + "?faces-redirect=true";
    }
}
