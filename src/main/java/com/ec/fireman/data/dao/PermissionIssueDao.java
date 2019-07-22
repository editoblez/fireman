package com.ec.fireman.data.dao;

import com.ec.fireman.data.entities.PermissionIssue;
import com.ec.fireman.data.entities.PermissionRequest;
import com.ec.fireman.data.entities.State;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.List;

@Log4j2
@Stateless
public class PermissionIssueDao extends GenericDaoImpl<PermissionIssue> {

  @PostConstruct
  public void init() {
    log.info("PermissionIssueDao was successfully created");
    setClazz(PermissionIssue.class);
  }

  public void inactivePreviousPermissionIssued(PermissionRequest selectedRequest) {
    Query query = entityManager.createQuery("select pi from PermissionIssue pi where state=:active and pi.inspectionHeader.permissionRequest.id=:permissionRequestId");
    query.setParameter("permissionRequestId", selectedRequest.getId());
    query.setParameter("active", State.ACTIVE);
    List permissionIssues = query.getResultList();
    if (CollectionUtils.isEmpty(permissionIssues)) {
      return;
    }
    PermissionIssue permissionIssue = (PermissionIssue) permissionIssues.get(0);
    permissionIssue.setState(State.INACTIVE);
    entityManager.merge(permissionIssues);
  }
}
