package com.ec.fireman.beans.user;

import com.ec.fireman.beans.PageNameConstants;
import com.ec.fireman.data.dao.RoleDao;
import com.ec.fireman.data.dao.UserAccountDao;
import com.ec.fireman.data.entities.Role;
import com.ec.fireman.data.entities.UserAccount;
import com.ec.fireman.util.PasswordUtil;
import com.github.adminfaces.template.util.Assert;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

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
public class UserFormBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private RoleDao roleDao;
    @Inject
    private UserAccountDao userAccountDao;

    private UserAccount userAccount;
    private Role role;
    private Long id;

    public void init() {
        if (Assert.has(id)) {
            userAccount = userAccountDao.findById(id);
            role = userAccount.getRole();
        } else {
            userAccount = new UserAccount();
            role = new Role();
        }
    }

    @Transactional
    public void createUser() {
        userAccount.setRole(role);
        userAccount.setPassword(PasswordUtil.encrypt(userAccount.getPassword()));
        userAccountDao.save(userAccount);
        userAccount = new UserAccount();
        id = null;
    }

    @Transactional
    public void editUser() {
        if (role.getId() != userAccount.getRole().getId())
            userAccount.setRole(role);
        userAccountDao.update(userAccount);
    }

    public List<Role> listRoles() {
        return roleDao.findAll();
    }

    public String redirectTo() throws IOException {
        return PageNameConstants.USER_ADMIN_PAGE + "?faces-redirect=true";
    }


    public void clear() {
        userAccount = new UserAccount();
        role = new Role();
        id = null;
    }

    public void save() {
        if (this.isNew()) {
            createUser();
        } else {
            editUser();
        }
    }

    public boolean isNew() {
        return userAccount == null || id == null || id == 0 || (Long) userAccount.getId() == null || userAccount.getId() <= 0;
    }
}
