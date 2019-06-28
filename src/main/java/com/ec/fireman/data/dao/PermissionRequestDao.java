package com.ec.fireman.data.dao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

import com.ec.fireman.data.entities.PermissionRequest;
import com.ec.fireman.data.entities.PermissionRequestStatus;
import com.ec.fireman.data.entities.State;

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

  public List<PermissionRequest> findPermissionRequestByPermissionRequestStatus(PermissionRequestStatus status) {
    try {
      return entityManager.createNamedQuery("findPermissionRequestByPermissionRequestStatus").setParameter("status", status)
          .getResultList();
    } catch (Exception ex) {
      log.error("Error to execute findPermissionRequestByPermissionRequestStatus", ex);
    }
    return null;
  }

}
