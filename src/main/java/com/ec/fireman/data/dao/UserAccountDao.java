package com.ec.fireman.data.dao;

import com.ec.fireman.data.entities.UserAccount;
import com.ec.fireman.util.UserUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

@Stateless
public class UserAccountDao extends GenericDaoImpl<UserAccount> {

  private static final Logger log = LogManager.getLogger(UserAccountDao.class);

  @PostConstruct
  public void init() {
    log.info("Setup fireman app");
    setClazz(UserAccount.class);
    if (CollectionUtils.isEmpty(findAll())) {
      createAdminUser();
    }
  }

  private void createAdminUser() {
    save(UserUtil.createDefaultAdminUtil());
  }

  public UserAccount findUserByCi(String ci) {
    UserAccount account = null;
    try {
      account = (UserAccount) entityManager.createNamedQuery("findUserByCi")
          .setParameter("ci", ci)
          .getSingleResult();
    } catch (Exception ex) {
      log.error("Error to execute named query findUserByCi", ex);
    }
    return account;
  }
}
