package com.ec.fireman.data.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Local implements BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private String id;

  @Column(nullable = false)
  private String address;

  @Column
  private String latitude;

  @Column
  private String longitude;

  @ManyToOne
  @JoinColumn
  private Service service;

  @ManyToOne
  @JoinColumn
  private Client client;

  @Enumerated(EnumType.STRING)
  private State state;

}
