package com.ec.fireman.data.entities;

import lombok.Data;

import javax.persistence.*;

@NamedQueries({ @NamedQuery(name = "findServiceByName", query = "from Service e where e.name = :name") })

@Data
@Entity
public class Service implements BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column
  private String name;

  @Enumerated(EnumType.STRING)
  private State state;

}
