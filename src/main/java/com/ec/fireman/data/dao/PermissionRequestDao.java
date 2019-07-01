package com.ec.fireman.data.dao;

import com.ec.fireman.data.entities.PermissionRequest;
import com.ec.fireman.data.entities.PermissionRequestStatus;
import com.ec.fireman.util.SessionUtils;
import lombok.extern.log4j.Log4j2;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQueryCreator;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import java.util.List;
import java.util.stream.Collectors;

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

  public List<PermissionRequest> findPermissionRequestByPermissionRequestStatus(PermissionRequestStatus status) {
    try {
      return entityManager.createNamedQuery("findPermissionRequestByPermissionRequestStatus")
          .setParameter("status", status).getResultList();
    } catch (Exception ex) {
      log.error("Error to execute findPermissionRequestByPermissionRequestStatus", ex);
    }
    return null;
  }

  public List<PermissionRequest> findAllByStatusAndUser(PermissionRequestStatus status, String inspectorId) {

    AuditQueryCreator reader = getReader().createQuery();

    List<PermissionRequest> list = (List<PermissionRequest>) reader.forRevisionsOfEntity(PermissionRequest.class, false, false)
        .add(AuditEntity.revisionNumber().maximize())
        .add(AuditEntity.property("permissionRequestStatus").eq(status))
        .add(AuditEntity.revisionProperty("username").eq(inspectorId))
        .getResultList().stream().map(it -> ((Object[]) it)[0]).collect(Collectors.toList());

    log.info(list.size());

    return list;
  }

  public List<PermissionRequest> findAllInProgressByLoggedUser() {
    return findAllByStatusAndUser(PermissionRequestStatus.IN_PROGRESS, SessionUtils.retrieveLoggedUser().getUserId());
  }

}
