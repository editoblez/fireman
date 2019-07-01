package com.ec.fireman.data.dao;

import org.hibernate.envers.AuditReader;

import java.io.Serializable;
import java.util.List;

public interface DaoFacade<T> extends Serializable {
  List<T> findAll();

  T findById(long id);

  void remove(T entity);

  void removeById(long id);

  T update(T entity);

  void save(T entity);

  AuditReader getReader();

}
