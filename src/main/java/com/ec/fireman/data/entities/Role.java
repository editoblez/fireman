package com.ec.fireman.data.entities;

import javax.persistence.*;

import static com.ec.fireman.data.entities.EntityConstants.GENERIC_COLUMN_SIZE;

@Entity
public class Role implements BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column(unique = true, length = GENERIC_COLUMN_SIZE, nullable = false)
  private String roleName;

  @Enumerated(EnumType.STRING)
  private State state;

  public Role() {
  }

  public Role(String roleName) {
    this.roleName = roleName;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

}

