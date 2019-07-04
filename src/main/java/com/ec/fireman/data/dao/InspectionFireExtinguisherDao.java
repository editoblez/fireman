package com.ec.fireman.data.dao;

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

}
