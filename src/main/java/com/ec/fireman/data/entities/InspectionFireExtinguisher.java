package com.ec.fireman.data.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.envers.Audited;

import lombok.Data;

@Audited
@Data
@Entity
public class InspectionFireExtinguisher implements BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column
  private String quantity;

  @Column
  private String type;

  @Column
  private String capacity;

  @Column
  private String status;

  @Column
  private String location;

  @ManyToOne
  private InspectionHeader inspectionHeader;

}
