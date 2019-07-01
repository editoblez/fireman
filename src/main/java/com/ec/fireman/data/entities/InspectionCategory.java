package com.ec.fireman.data.entities;

import lombok.Data;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Audited
@Data
@Entity
public class InspectionCategory implements BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column
  private String name;

  @Column
  private String showName;

  @Column
  private int section;

  @Enumerated(EnumType.STRING)
  private State state;

}
