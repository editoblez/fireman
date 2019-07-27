package com.ec.fireman.beans;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.ec.fireman.data.dao.PermissionIssueDao;
import com.ec.fireman.data.entities.PermissionIssue;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Data
@Log4j2
@Named
@ViewScoped
public class PermissionIssueReportBean implements Serializable {

  private static final long serialVersionUID = -7266990896196309678L;

  @Inject
  private PermissionIssueDao permissionIssueDao;

  private List<PermissionIssue> requests;
  private Date from, to;

  @PostConstruct
  public void init() {
    from = Calendar.getInstance().getTime();
    to = Calendar.getInstance().getTime();
    this.refreshReport();
  }

  public void refreshReport() {
    requests = permissionIssueDao.findByTime(from, to);
    log.info("Permissions length: " + (requests != null ? requests.size() : 0));
  }

}
