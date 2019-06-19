package com.ec.fireman.data.dao;

import com.ec.fireman.data.entities.BaseEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public abstract class GenericDaoImpl<T extends BaseEntity> implements DaoFacade<T> {

  private static final Logger log = LogManager.getLogger();
  @PersistenceContext(unitName = "FiremanPersistenceUnit")
  EntityManager entityManager;
  private Class<T> clazz;

  public void setClazz(Class<T> clazz) {
    this.clazz = clazz;
  }

  @Override
  public List<T> findAll() {
    return entityManager.createQuery("from " + clazz.getName()).getResultList();
  }

  @Override
  public T findById(long id) {
    return entityManager.find(clazz, id);
  }

  @Override
  public void remove(T entity) {
    entityManager.remove(entity);
  }

  @Override
  public void removeById(long id) {
    T entity = findById(id);
    remove(entity);
  }

  @Override
  public T update(T entity) {
    return entityManager.merge(entity);
  }

  @Override
  public void save(T entity) {
    entityManager.persist(entity);
  }

}
