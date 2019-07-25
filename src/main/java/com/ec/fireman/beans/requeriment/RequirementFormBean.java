package com.ec.fireman.beans.requeriment;

import com.ec.fireman.beans.PageNameConstants;
import com.ec.fireman.data.dao.RequirementDao;
import com.ec.fireman.data.dao.RoleDao;
import com.ec.fireman.data.entities.Requirement;
import com.ec.fireman.data.entities.Role;
import com.ec.fireman.data.entities.State;
import com.github.adminfaces.template.util.Assert;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.omnifaces.util.Faces;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@Data
@Log4j2
@Named
@ViewScoped
public class RequirementFormBean implements Serializable {

    private static final long serialVersionUID = -1L;

    @Inject
    private RequirementDao requirementDao;
    @Inject
    private RoleDao roleDao;

    private Long id;
    private Requirement requirement;
    private Role role;

    public void init() {
        if (Assert.has(id)) {
            requirement = requirementDao.findById(id);
            setRole(requirement.getRole());
        }
        else {
            role = new Role();
            requirement = new Requirement();
        }
    }


    @Transactional
    public void createRequirement() {
        requirement.setState(State.ACTIVE);
        requirement.setRole(role);
        requirementDao.save(requirement);
        requirement = new Requirement();
        id = null;
    }

    @Transactional
    public void editRequirement() {
        if (role.getId() != requirement.getRole().getId())
            requirement.setRole(role);
        requirement.setState(State.ACTIVE);
        requirementDao.update(requirement);
    }


    public List<Role> listRoles() {
        return roleDao.findAll();
    }

    public String redirectTo() throws IOException {
        return PageNameConstants.REQUIREMENT_ADMIN_PAGE + "?faces-redirect=true";
    }


    public void clear() {
        requirement = new Requirement();
        id = null;
    }

    public void save() {
        if (this.isNew()) {
            createRequirement();
        } else {
            editRequirement();
        }
    }

    public boolean isNew() {
        return requirement == null || id == null || id == 0 || (Long)requirement.getId() == null || requirement.getId() <= 0;
    }
}
