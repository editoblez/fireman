package com.ec.fireman.data.entities;

import lombok.Data;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Calendar;

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

  @Column
  private long time;

  @ManyToOne
  private InspectionHeader inspectionHeader;

  public PermissionIssue(BigDecimal price, InspectionHeader inspectionHeader) {
    this.price = price;
    this.inspectionHeader = inspectionHeader;
    this.time = Calendar.getInstance().getTimeInMillis();
    this.state = State.ACTIVE;
  }
}
