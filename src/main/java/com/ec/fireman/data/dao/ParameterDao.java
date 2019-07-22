package com.ec.fireman.data.dao;

import com.ec.fireman.data.entities.Parameter;
import lombok.extern.log4j.Log4j2;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

@Log4j2
@Stateless
public class ParameterDao extends GenericDaoImpl<Parameter> {

  private static final String MONTH_QUANTITY_CLOSE_TO_EXPIRE = "MONTH_QUANTITY_CLOSE_TO_EXPIRE";
  private static final String MONTH_QUANTITY_TO_EXPIRE = "MONTH_QUANTITY_TO_EXPIRE";
  private static final int DEFAULT_MONTH_QUANTITY_CLOSE_TO_EXPIRE = 11;
  private static final int DEFAULT_MONTH_QUANTITY_TO_EXPIRE = 12;

  @PostConstruct
  public void init() {
    log.info("ParameterDao was successfully created");
    setClazz(Parameter.class);
  }

  public Parameter findParameterByName(String name) {
    try {
      return (Parameter) entityManager.createNamedQuery("findParameterByName").setParameter("name", name).getSingleResult();
    } catch (Exception ex) {
      log.error("Error to execute findParameterByName", ex);
    }
    return null;
  }

  public int findMonthQuantityCloseToAddToExpire() {
    try {
      return Integer.parseInt(findParameterByName(MONTH_QUANTITY_CLOSE_TO_EXPIRE).getValue());
    } catch (Exception e) {
      return DEFAULT_MONTH_QUANTITY_CLOSE_TO_EXPIRE;
    }
  }

  public int findMonthQuantityToAddToExpire() {
    try {
      return Integer.parseInt(findParameterByName(MONTH_QUANTITY_TO_EXPIRE).getValue());
    } catch (Exception e) {
      return DEFAULT_MONTH_QUANTITY_TO_EXPIRE;
    }
  }
}
