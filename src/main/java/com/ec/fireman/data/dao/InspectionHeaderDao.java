package com.ec.fireman.data.dao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

import com.ec.fireman.data.entities.InspectionHeader;

import lombok.extern.log4j.Log4j2;

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
      List<InspectionHeader> list = entityManager.createNamedQuery("findInspectionHeaderByRequest")
          .setParameter("permissionRequestId", permissionRequestId).getResultList();
      return list != null && !list.isEmpty() ? list.get(0) : null;
    } catch (Exception ex) {
      log.error("Error to execute findInspectionHeaderByRequest", ex);
    }
    return null;
  }

}
