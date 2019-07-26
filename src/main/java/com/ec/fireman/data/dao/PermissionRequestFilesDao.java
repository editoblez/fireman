package com.ec.fireman.data.dao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

import com.ec.fireman.data.entities.PermissionRequestFiles;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Stateless
public class PermissionRequestFilesDao extends GenericDaoImpl<PermissionRequestFiles> {

  @PostConstruct
  public void init() {
    log.info("PermissionRequestFilesDao was successfully created");
    setClazz(PermissionRequestFiles.class);
  }

  public List<PermissionRequestFiles> findPermissionRequestFilesByRequest(long requestId) {
    try {
      return entityManager.createNamedQuery("findPermissionRequestFilesByRequest").setParameter("requestId", requestId)
          .getResultList();
    } catch (Exception ex) {
      log.error("Error to execute findPermissionRequestFilesByRequest", ex);
    }
    return null;
  }

  public List<PermissionRequestFiles> findFilesByRequestAndRequirement(long requestId, long reqId) {
    try {
      return entityManager.createNamedQuery("findFilesByRequestAndRequirement")
              .setParameter("requestId", requestId)
              .setParameter("reqId", reqId)
              .getResultList();
    } catch (Exception ex) {
      log.error("Error to execute findFilesByRequestAndRequirement", ex);
    }
    return null;
  }

}
