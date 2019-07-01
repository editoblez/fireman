package com.ec.fireman.data.dao;

import com.ec.fireman.data.entities.InspectionCategory;
import lombok.extern.log4j.Log4j2;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

@Log4j2
@Stateless
public class InspectionCategoryDao extends GenericDaoImpl<InspectionCategory> {

  @PostConstruct
  public void init() {
    log.info("InspectionCategoryDao was successfully created");
    setClazz(InspectionCategory.class);
  }

}
