package com.ec.fireman.data.dao;

import com.ec.fireman.data.entities.InspectionDetail;
import lombok.extern.log4j.Log4j2;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

@Log4j2
@Stateless
public class InspectionDetailDao extends GenericDaoImpl<InspectionDetail> {

  @PostConstruct
  public void init() {
    log.info("InspectionDetailDao was successfully created");
    setClazz(InspectionDetail.class);
  }

}
