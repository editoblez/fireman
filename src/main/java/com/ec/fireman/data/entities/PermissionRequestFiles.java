package com.ec.fireman.data.entities;

import javax.persistence.*;

@Entity
public class PermissionRequestFiles implements BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Lob
  @Column(length = 100000)
  private byte[] data;

  @ManyToOne
  private Requierement requierement;

  @ManyToOne
  private PermissionRequest permissionRequest;

  @Enumerated(EnumType.STRING)
  private State state;

}
