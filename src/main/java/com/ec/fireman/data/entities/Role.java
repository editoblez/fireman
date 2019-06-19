package com.ec.fireman.data.entities;

import lombok.Data;

import javax.persistence.*;

import static com.ec.fireman.data.entities.EntityConstants.GENERIC_COLUMN_SIZE;

@Data
@Entity
public class Role implements BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column(unique = true, length = GENERIC_COLUMN_SIZE, nullable = false)
  private String roleName;

  @Enumerated(EnumType.STRING)
  private State state;

}

