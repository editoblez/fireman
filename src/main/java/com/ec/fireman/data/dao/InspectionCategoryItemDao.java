package com.ec.fireman.data.dao;

import com.ec.fireman.data.entities.InspectionCategoryItem;
import lombok.extern.log4j.Log4j2;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

@Log4j2
@Stateless
public class InspectionCategoryItemDao extends GenericDaoImpl<InspectionCategoryItem> {

  @PostConstruct
  public void init() {
    log.info("InspectionCategoryItemDAO was successfully created");
    setClazz(InspectionCategoryItem.class);
  }

}
