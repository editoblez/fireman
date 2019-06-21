package com.ec.fireman.data.entities;

import lombok.Data;

import javax.persistence.*;

@NamedQueries({
    @NamedQuery(
        name = "findRequirementByName",
        query = "from Requirement e where e.name = :name"
    )
})

@Data
@Entity
public class Requirement implements BaseEntity {

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
