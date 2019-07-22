package com.ec.fireman.data.entities;

import lombok.Data;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.math.BigDecimal;

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

  @Column
  private long closeToExpire;

  @Column
  private long expire;

  @ManyToOne
  private InspectionHeader inspectionHeader;

  public PermissionIssue(BigDecimal price, InspectionHeader inspectionHeader, long time, long closeToExpire, long expire) {
    this.price = price;
    this.inspectionHeader = inspectionHeader;
    this.time = time;
    this.closeToExpire = closeToExpire;
    this.expire = expire;
    this.state = State.ACTIVE;
  }

  public PermissionIssue() {
  }
}
