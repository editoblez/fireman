package com.ec.fireman.data.entities;

import com.ec.fireman.data.representation.User;
import com.ec.fireman.util.SessionUtils;
import org.hibernate.envers.RevisionListener;

import static com.ec.fireman.util.UserUtil.DEFAULT_ADMIN_CI;

public class UserRevisionListener implements RevisionListener {
  @Override
  public void newRevision(Object revisionEntity) {
    UserRevEntity userRevEntity = (UserRevEntity) revisionEntity;
    User loggedUser = SessionUtils.retrieveLoggedUser();

    userRevEntity.setUsername(loggedUser != null && loggedUser.getUserId() != null
        ? loggedUser.getUserId() : DEFAULT_ADMIN_CI);
  }
}
