package com.ec.fireman.data.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.hibernate.envers.Audited;

import lombok.Data;

@NamedQueries({
    @NamedQuery(name = "findInspectionHeaderByRequest", query = "from InspectionHeader i where i.permissionRequest.id = :permissionRequestId") })

@Audited
@Data
@Entity
public class InspectionHeader implements BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column
  private boolean concrete;
  @Column
  private boolean metallicStructure;
  @Column
  private boolean mixed;
  @Column
  private boolean block;
  @Column
  private boolean brick;
  @Column
  private boolean adobe;
  @Column
  private boolean installationsGood;
  @Column
  private boolean installationsBad;
  @Column
  private boolean installationsInternal;
  @Column
  private boolean installationsExternal;
  @Column
  private boolean ventilationNatural;
  @Column
  private boolean ventilationMechanic;
  @Column
  private boolean ventilationAdequate;
  @Column
  private boolean ventilationScarce;
  @Column
  private boolean knowledgeExtinction;
  @Column
  private boolean alarms;
  @Column
  private boolean fireDetectors;
  @Column
  private boolean smokeDetectors;
  @Column
  private boolean emergencyLights;
  @Column
  private String riskFire;
  @Column
  private String recommendations;
  @Column
  private String observations;

  @Enumerated(EnumType.STRING)
  private State state;

  @ManyToOne
  private PermissionRequest permissionRequest;

}
