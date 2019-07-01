package com.ec.fireman.data.entities;

import lombok.Data;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.math.BigDecimal;

@NamedQueries({@NamedQuery(name = "findServiceByName", query = "from Service e where e.name = :name")})

@Audited
@Data
@Entity
public class PermissionIssue implements BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column
  private BigDecimal price;

  @Enumerated(EnumType.STRING)
  private State state;

  @ManyToOne
  private InspectionHeader inspectionHeader;

}
