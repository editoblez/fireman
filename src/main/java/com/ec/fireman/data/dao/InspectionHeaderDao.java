package com.ec.fireman.data.dao;

import com.ec.fireman.data.entities.InspectionHeader;
import lombok.extern.log4j.Log4j2;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

@Log4j2
@Stateless
public class InspectionHeaderDao extends GenericDaoImpl<InspectionHeader> {

  @PostConstruct
  public void init() {
    log.info("InspectionHeaderDao was successfully created");
    setClazz(InspectionHeader.class);
  }

}
