package com.ec.fireman.beans.clientelocal;

import com.ec.fireman.beans.PageNameConstants;
import com.ec.fireman.data.dao.LocalDao;
import com.ec.fireman.data.dao.PermissionRequestDao;
import com.ec.fireman.data.dao.ServiceDao;
import com.ec.fireman.data.dao.UserAccountDao;
import com.ec.fireman.data.entities.*;
import com.ec.fireman.util.SessionUtils;
import com.github.adminfaces.template.util.Assert;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

@Data
@Log4j2
@Named
@ViewScoped
public class LocalFormBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private LocalDao localDao;
    @Inject
    private ServiceDao serviceDao;
    @Inject
    private PermissionRequestDao permissionRequestDao;
    @Inject
    private UserAccountDao userAccountDao;

    private Local local;
    private Long id;
    private Service service;

    public void init() {
        if (Assert.has(id) && id != null) {
            local = localDao.findById(id);
            service = local.getService();
        } else {
            local = new Local();
            service = new Service();
        }
    }

    @Transactional
    public void createLocal() {
        local.setUserAccount(userAccountDao.findUserByCi(SessionUtils.retrieveLoggedUser().getUserId()));
        local.setState(State.ACTIVE);
        local.setService(service);
        log.info(local.toString());
        PermissionRequest permissionRequest = new PermissionRequest(PermissionRequestStatus.TO_REQUEST, local);
        permissionRequestDao.save(permissionRequest);
        clear();
    }

    @Transactional
    public void editLocal() {
        if (service.getId() != local.getService().getId())
            local.setService(service);
        log.info(local.toString());
        localDao.update(local);
    }

    public List<Service> listServices() {
        return serviceDao.findAll();
    }

    public boolean isNew() {
        return local == null || id == null || id == 0 || (Long) local.getId() == null || local.getId() <= 0;
    }

    public void save() {
        if (this.isNew())
            this.createLocal();
        else
            this.editLocal();
    }

    public void clear() {
        local = new Local();
        service = new Service();
        id = null;
    }

    public String redirectTo() {
        return PageNameConstants.LOCAL_CLIENT_PAGE + "?faces-redirect=true";
    }
}
