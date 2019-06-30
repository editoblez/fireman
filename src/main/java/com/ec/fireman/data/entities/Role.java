package com.ec.fireman.data.entities;

import lombok.Data;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@NamedQueries({@NamedQuery(name = "findRoleByName", query = "from Role e where e.roleName = :roleName")})

@Audited
@Data
@Entity
public class Role implements BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Enumerated(EnumType.STRING)
  @Column(unique = true)
  private RoleTypes roleName;

  @Enumerated(EnumType.STRING)
  private State state;

  public Role() {
    this.state = State.ACTIVE;
  }

  public Role(RoleTypes roleName) {
    this();
    this.roleName = roleName;
  }

  public long getId() {
    return id;
  }
}
