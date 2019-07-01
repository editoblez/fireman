package com.ec.fireman.data.entities;

import lombok.Data;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

import javax.persistence.Entity;

@Data
@Entity
@RevisionEntity(UserRevisionListener.class)
class UserRevEntity extends DefaultRevisionEntity {
  private String username;
}
