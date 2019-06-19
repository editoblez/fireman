package com.ec.fireman.data.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Service implements BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column
  private String name;
}
