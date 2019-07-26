package com.ec.fireman.data.dao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.Query;

import com.ec.fireman.data.entities.PermissionRequest;
import com.ec.fireman.data.entities.PermissionRequestStatus;
import com.ec.fireman.util.SessionUtils;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Stateless
public class PermissionRequestDao extends GenericDaoImpl<PermissionRequest> {

  @PostConstruct
  public void init() {
    log.info("PermissionRequestDao was successfully created");
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

  public List findPermissionRequestByPermissionRequestStatus(PermissionRequestStatus status) {
    try {
      return entityManager.createNamedQuery("findPermissionRequestByPermissionRequestStatus")
          .setParameter("status", status).getResultList();
    } catch (Exception ex) {
      log.error("Error to execute findPermissionRequestByPermissionRequestStatus", ex);
    }
    return null;
  }

  public List findAllByStatusAndUser(PermissionRequestStatus status, String inspectorId) {
    try {
      return entityManager.createNamedQuery("findPermissionRequestByStatusAndInspector").setParameter("status", status)
          .setParameter("inspector", inspectorId).getResultList();
    } catch (Exception ex) {
      log.error("Error to execute PermissionRequestStatus", ex);
    }
    return null;
  }

  public List<PermissionRequest> findAllByInspector(String inspector) {
    Query query = entityManager.createQuery("select pr from PermissionRequest pr "
        + "where pr.inspector.ci like :inspector or pr.inspector.firstName like :inspector "
        + "or pr.inspector.firstLastName like :inspector ", PermissionRequest.class);
    query.setParameter("inspector", "%" + inspector + "%");
    return query.getResultList();
  }

  public List findAllInProgressByLoggedUser() {
    return findAllByStatusAndUser(PermissionRequestStatus.IN_PROGRESS, SessionUtils.retrieveLoggedUser().getUserId());
  }

  public List<PermissionRequest> findAllPermissionRequestToExpire() {
    return findPermissionRequestByPermissionRequestStatus(PermissionRequestStatus.TO_EXPIRE);
  }

  public List<PermissionRequest> findAllPermissionRequestExpired() {
    return findPermissionRequestByPermissionRequestStatus(PermissionRequestStatus.EXPIRED);
  }
}
