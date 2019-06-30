package com.ec.fireman.data.entities;

import lombok.Data;

import javax.persistence.*;

@NamedQueries({@NamedQuery(name = "findLocalByUser", query = "from Local l where l.userAccount.ci = :ci")})

@Data
@Entity
public class Local implements BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column(nullable = false)
  private String address;

  @Column
  private String mapUrl;

  @ManyToOne
  @JoinColumn
  private Service service;

  @ManyToOne
  @JoinColumn
  private UserAccount userAccount;

  @Enumerated(EnumType.STRING)
  private State state;

  @OneToOne(mappedBy = "local")
  private PermissionRequest permissionRequest;

}
