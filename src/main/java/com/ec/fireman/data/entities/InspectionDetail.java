package com.ec.fireman.data.entities;

import lombok.Data;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Audited
@Data
@Entity
public class InspectionDetail implements BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column
  private String value;

  @ManyToOne
  private InspectionHeader inspectionHeader;

  @ManyToOne
  private InspectionCategoryItem item;

}
