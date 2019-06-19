package com.ec.fireman.data.entities;

import javax.persistence.*;

@Entity
public class Requierement implements BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column
  private String name;

  @Column
  private String description;

  @ManyToOne
  private Role role;

  @Enumerated(EnumType.STRING)
  private State state;

}
