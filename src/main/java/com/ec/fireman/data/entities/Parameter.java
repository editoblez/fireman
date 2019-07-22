package com.ec.fireman.data.entities;

import lombok.Data;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@NamedQueries({@NamedQuery(name = "findParameterByName", query = "from Parameter p where p.name = :name")})
@Audited
@Data
@Entity
public class Parameter implements BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column
  private String name;

  @Column
  private String value;

  @Enumerated(EnumType.STRING)
  private State state;
}
