package com.ec.fireman.data.entities;

import javax.persistence.*;

@Entity
public class Requierement implements BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column
  private String name;

  @ManyToOne
  private Role role;

}
