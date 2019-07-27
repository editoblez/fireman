package com.ec.fireman.data.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.Query;

import org.apache.commons.collections4.CollectionUtils;

import com.ec.fireman.data.entities.PermissionIssue;
import com.ec.fireman.data.entities.PermissionRequest;
import com.ec.fireman.data.entities.State;

import lombok.extern.log4j.Log4j2;

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

  public List<PermissionIssue> findAllCloseToExpire() {
    Query query = entityManager.createQuery("select pi from PermissionIssue pi where pi.state=:active and :currentDate > pi.closeToExpire " +
        "and :currentDate <= pi.expire", PermissionIssue.class);
    query.setParameter("active", State.ACTIVE);
    query.setParameter("currentDate", Calendar.getInstance().getTimeInMillis());
    return query.getResultList();
  }

  public List<PermissionIssue> findAllExpired() {
    Query query = entityManager.createQuery("select pi from PermissionIssue pi where pi.state=:active and :currentDate > pi.expire", PermissionIssue.class);
    query.setParameter("active", State.ACTIVE);
    query.setParameter("currentDate", Calendar.getInstance().getTimeInMillis());
    return query.getResultList();
  }
  
  public List<PermissionIssue> findByTime(Date from, Date to) {
    Query query = entityManager.createQuery("select pi from PermissionIssue pi where pi.time between :dateFrom and :dateTo", PermissionIssue.class);
    query.setParameter("dateFrom", from.getTime());
    query.setParameter("dateTo", to.getTime());
    return query.getResultList();
  }
}
