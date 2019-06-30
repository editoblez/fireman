package com.ec.fireman.data.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class PermissionRequestStatusLog implements BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Temporal(value = TemporalType.TIMESTAMP)
  @Column
  private Date date;

  @ManyToOne
  @JoinColumn
  private PermissionRequest permissionRequest;

  @Enumerated(EnumType.STRING)
  private PermissionRequestStatus previousRequestStatus;

  @Enumerated(EnumType.STRING)
  private PermissionRequestStatus currentRequestStatus;

  public PermissionRequestStatusLog() {
  }

  public PermissionRequestStatusLog(Date date, PermissionRequest permissionRequest, PermissionRequestStatus previousRequestStatus, PermissionRequestStatus currentRequestStatus) {
    this.date = date;
    this.permissionRequest = permissionRequest;
    this.previousRequestStatus = previousRequestStatus;
    this.currentRequestStatus = currentRequestStatus;
  }
}
