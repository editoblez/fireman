package com.ec.fireman.data.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Permission implements BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column
  @Enumerated(EnumType.STRING)
  private PermissionStatus permissionStatus;

  @Temporal(value = TemporalType.TIMESTAMP)
  @Column
  private Date date;

  @ManyToOne
  private PermissionRequest permissionRequest;

  @Lob
  @Column(length = 100000)
  private byte[] data;

  @Enumerated(EnumType.STRING)
  private State state;

}
