package com.ec.fireman.data.dao;

import com.ec.fireman.data.entities.UserAccount;
import com.ec.fireman.util.UserUtil;
import org.apache.commons.collections4.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

@Stateless
public class UserAccountDao extends GenericDaoImpl<UserAccount> {

  @PostConstruct
  public void init() {
    System.out.println("Setup fireman app");
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
      //TODO here must be a logging with the exception
    }
    return account;
  }
}
