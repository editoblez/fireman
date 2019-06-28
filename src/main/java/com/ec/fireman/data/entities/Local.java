package com.ec.fireman.data.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import lombok.Data;

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

}
