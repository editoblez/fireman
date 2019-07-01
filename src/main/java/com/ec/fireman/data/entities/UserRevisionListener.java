package com.ec.fireman.data.entities;

import com.ec.fireman.util.SessionUtils;
import org.hibernate.envers.RevisionListener;

import java.util.Objects;

public class UserRevisionListener implements RevisionListener {
  @Override
  public void newRevision(Object revisionEntity) {
    UserRevEntity exampleRevEntity = (UserRevEntity) revisionEntity;
    exampleRevEntity.setUsername(Objects.requireNonNull(SessionUtils.retrieveLoggedUser()).getUserId());
  }
}
