package com.ec.fireman.batch;

import com.ec.fireman.data.dao.PermissionIssueDao;
import com.ec.fireman.data.entities.PermissionIssue;
import com.ec.fireman.data.entities.PermissionRequestStatus;
import lombok.extern.log4j.Log4j2;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timer;
import javax.inject.Inject;
import java.util.List;

@Startup
@Singleton
@Log4j2
public class ScheduledTasks {

  @Inject
  PermissionIssueDao permissionIssueDao;

  @Schedule(hour = "02", minute = "21", second = "30", info = "Daily")
  public void automaticallyScheduled(Timer timer) {
    log.info("Running batch process ...");
    updateAllCloseToExpireStatus();
    updateAllExpiredStatus();
    log.info("End of batch ...");
  }

  private void updateAllCloseToExpireStatus() {
    List<PermissionIssue> permissionRequestsCloseToExpire = permissionIssueDao.findAllCloseToExpire();
    for (PermissionIssue permissionIssue : permissionRequestsCloseToExpire) {
      permissionIssue.getInspectionHeader().getPermissionRequest().setPermissionRequestStatus(PermissionRequestStatus.TO_EXPIRE);
      permissionIssueDao.update(permissionIssue);
    }
  }

  private void updateAllExpiredStatus() {
    List<PermissionIssue> permissionRequestsCloseToExpire = permissionIssueDao.findAllExpired();
    for (PermissionIssue permissionIssue : permissionRequestsCloseToExpire) {
      permissionIssue.getInspectionHeader().getPermissionRequest().setPermissionRequestStatus(PermissionRequestStatus.EXPIRED);
      permissionIssueDao.update(permissionIssue);
    }
  }

}
