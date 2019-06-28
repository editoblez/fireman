package com.ec.fireman.data.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class PermissionRequestFiles implements BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Lob
  @Column(length = 100000)
  private byte[] data;
  
  @Column
  private String fileName;

  @ManyToOne
  private Requirement requirement;

  @ManyToOne
  private PermissionRequest permissionRequest;

  @Enumerated(EnumType.STRING)
  private State state;

}
