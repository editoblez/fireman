package com.ec.fireman.data.dao;

import com.ec.fireman.data.entities.RoleTypes;
import com.ec.fireman.data.entities.UserAccount;
import com.ec.fireman.util.UserUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Log4j2
@Stateless
public class UserAccountDao extends GenericDaoImpl<UserAccount> {
  @Inject
  RoleDao roleDao;

  @PostConstruct
  public void init() {
    log.info("Setup fireman app");
    setClazz(UserAccount.class);
    if (CollectionUtils.isEmpty(findAll())) {
      createAdminUser();
    }
  }

  private void createAdminUser() {
    save(UserUtil.createDefaultAdminUtil(roleDao.findRolByName(RoleTypes.ADMIN)));
  }

  public UserAccount findUserByCi(String ci) {
    UserAccount account = null;
    try {
      account = (UserAccount) entityManager.createNamedQuery("findUserByCi")
          .setParameter("ci", ci)
          .getSingleResult();
    } catch (Exception ex) {
      log.error("Error to exceute findUserByCi: ", ex);
    }
    return account;
  }
}
