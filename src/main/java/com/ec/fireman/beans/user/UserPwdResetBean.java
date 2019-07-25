package com.ec.fireman.beans.user;

import com.ec.fireman.beans.PageNameConstants;
import com.ec.fireman.data.dao.UserAccountDao;
import com.ec.fireman.data.entities.UserAccount;
import com.ec.fireman.util.PasswordUtil;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

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
public class UserPwdResetBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private static final String MSG_ERROR_PASSWORD = "Las contrase�as no coinciden";

    @Inject
    private UserAccountDao userAccountDao;

    private UserAccount userAccount;

    private String passReset1;
    private String passReset2;
    private Long id;

    public void init() {
        userAccount = userAccountDao.findById(id);
    }

    @Transactional
    public String resetPassword() {
        if (!passReset1.equals(passReset2)) {
            return MSG_ERROR_PASSWORD;
        }
        userAccount.setPassword(PasswordUtil.encrypt(passReset1));
        userAccountDao.update(userAccount);
        userAccount = new UserAccount();
        return "OK";
    }

    public String redirectTo() throws IOException {
        return PageNameConstants.USER_ADMIN_PAGE + "?faces-redirect=true";
    }
}
