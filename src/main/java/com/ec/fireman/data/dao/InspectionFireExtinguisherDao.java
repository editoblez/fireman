package com.ec.fireman.data.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

import com.ec.fireman.data.entities.InspectionFireExtinguisher;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Stateless
public class InspectionFireExtinguisherDao extends GenericDaoImpl<InspectionFireExtinguisher> {

  @PostConstruct
  public void init() {
    log.info("InspectionFireExtinguisherDao was successfully created");
    setClazz(InspectionFireExtinguisher.class);
  }

  public List<InspectionFireExtinguisher> findInspectionFireExtinguisherByHeader(long headerId) {
    try {
      List<InspectionFireExtinguisher> list = entityManager.createNamedQuery("findInspectionFireExtinguisherByHeader")
          .setParameter("headerId", headerId).getResultList();
      return list != null ? list : new ArrayList<InspectionFireExtinguisher>();
    } catch (Exception ex) {
      log.error("Error to execute findInspectionFireExtinguisherByHeader", ex);
    }
    return null;
  }

  public void removeInspectionFireExtinguisherByHeader(long headerId) {
    try {
      entityManager.createNamedQuery("removeInspectionFireExtinguisherByHeader").setParameter("headerId", headerId)
          .executeUpdate();
    } catch (Exception ex) {
      log.error("Error to execute removeInspectionFireExtinguisherByHeader", ex);
    }
  }

}
