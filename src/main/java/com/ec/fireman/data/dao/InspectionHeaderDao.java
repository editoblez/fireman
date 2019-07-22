package com.ec.fireman.data.dao;

import com.ec.fireman.data.entities.InspectionHeader;
import com.ec.fireman.data.entities.PermissionRequest;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.Query;
import java.util.List;

@Log4j2
@Stateless
public class InspectionHeaderDao extends GenericDaoImpl<InspectionHeader> {

  @PostConstruct
  public void init() {
    log.info("InspectionHeaderDao was successfully created");
    setClazz(InspectionHeader.class);
  }

  public InspectionHeader findInspectionHeaderByRequest(long permissionRequestId) {
    try {
      List list = entityManager.createNamedQuery("findInspectionHeaderByRequest")
          .setParameter("permissionRequestId", permissionRequestId).getResultList();
      return !CollectionUtils.isEmpty(list) ? (InspectionHeader) list.get(0) : null;
    } catch (Exception ex) {
      log.error("Error to execute findInspectionHeaderByRequest", ex);
    }
    return null;
  }

  public InspectionHeader findByPermissionRequest(PermissionRequest selectedRequest) {
    Query query = entityManager.createQuery("select h from InspectionHeader h where h.permissionRequest.id=:permissionRequestId order by id desc", InspectionHeader.class);
    query.setParameter("permissionRequestId", selectedRequest.getId());
    return (InspectionHeader) query.getResultList().get(0);
  }
}
