package com.ec.fireman.data.entities;

import lombok.Data;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Audited
@Data
@Entity
public class InspectionCategoryItem implements BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column
  private String name;

  @Column
  private int section;

  @Column
  private boolean variableCant;

  @Enumerated(EnumType.STRING)
  private State state;

  @Enumerated(EnumType.STRING)
  private InspectionCategoryItemType type;

  @ManyToOne
  private InspectionCategory inspectionCategory;

}
