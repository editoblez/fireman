package com.ec.fireman.data.entities;

import lombok.Data;

import javax.persistence.*;

@NamedQueries({@NamedQuery(name = "findPermissionRequestByLocal", query = "from PermissionRequest pr where pr.local.id = :localId")})

@Data
@Entity
public class PermissionRequest implements BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Enumerated(EnumType.STRING)
  private PermissionRequestStatus permissionRequestStatus;

  @ManyToOne
  private Local local;

  @Enumerated(EnumType.STRING)
  private State state;

}
