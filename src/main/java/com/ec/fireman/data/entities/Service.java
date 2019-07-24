package com.ec.fireman.data.entities;

import lombok.Data;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@NamedQueries(
      {
              @NamedQuery(name = "findServiceByName", query = "from Service e where e.name = :name"),
              @NamedQuery(name = "findServicesByName", query = "from Service e where e.name LIKE :name")
      }
)

@Audited
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
