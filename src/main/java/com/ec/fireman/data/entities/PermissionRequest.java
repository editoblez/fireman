package com.ec.fireman.data.entities;

import javax.persistence.*;

@Entity
public class PermissionRequest implements BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Enumerated(EnumType.STRING)
  private PermissionRequestStatus permissionRequestStatus;

  @ManyToOne
  private Local local;

}
