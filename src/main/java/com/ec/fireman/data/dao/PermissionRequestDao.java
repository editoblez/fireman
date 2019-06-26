package com.ec.fireman.data.dao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

import com.ec.fireman.data.entities.PermissionRequest;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Stateless
public class PermissionRequestDao extends GenericDaoImpl<PermissionRequest> {

  @PostConstruct
  public void init() {
    log.info("LocalDao was successfully created");
    setClazz(PermissionRequest.class);
  }

  public PermissionRequest findPermissionRequestByLocal(long localId) {
    try {
      List<PermissionRequest> list = entityManager.createNamedQuery("findPermissionRequestByLocal")
          .setParameter("localId", localId).getResultList();
      return list != null && !list.isEmpty() ? list.get(0) : null;
    } catch (Exception ex) {
      log.error("Error to execute findPermissionRequestByLocal", ex);
    }
    return null;
  }

}
