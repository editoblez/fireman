package com.ec.fireman.beans.service;


import com.ec.fireman.beans.PageNameConstants;
import com.ec.fireman.data.dao.ServiceDao;
import com.ec.fireman.data.entities.Service;
import com.ec.fireman.data.entities.State;
import com.github.adminfaces.template.util.Assert;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.omnifaces.util.Faces;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.Serializable;

@Data
@Log4j2
@Named
@ViewScoped
public class ServiceFormBean implements Serializable {

    private static final long serialVersionUID = -5468228478359216158L;
    @Inject
    private ServiceDao serviceDao;

    private Long id;

    private Service service;

    public void init() {
        if (Faces.isAjaxRequest()) {
            return;
        }
        if (Assert.has(id))
            service = serviceDao.findById(id);
        else
            service = new Service();
    }

    @Transactional
    public void createService() {
        service.setState(State.ACTIVE);
        serviceDao.save(service);
        clear();
    }

    @Transactional
    public void editService() {
        serviceDao.update(service);
    }

    public void save() {
        if (this.isNew()) {
            this.createService();
        } else {
            this.editService();
        }
    }

    public String redirectTo() throws IOException {
        return PageNameConstants.SERVICE_ADMIN_PAGE + "?faces-redirect=true";
    }


    public void clear() {
        service = new Service();
        id = null;
    }

    public boolean isNew() {
        return service == null || (Long)service.getId() == null || service.getId() <= 0;
    }
}
