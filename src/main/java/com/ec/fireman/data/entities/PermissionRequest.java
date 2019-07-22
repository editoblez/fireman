package com.ec.fireman.data.entities;

import lombok.Data;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@NamedQueries({
    @NamedQuery(name = "findPermissionRequestByLocal", query = "from PermissionRequest pr where pr.local.id = :localId"),
    @NamedQuery(name = "findPermissionRequestByPermissionRequestStatus", query = "from PermissionRequest pr where pr.permissionRequestStatus = :status"),
    @NamedQuery(name = "findPermissionRequestByStatusAndInspector", query = "from PermissionRequest pr where pr.permissionRequestStatus = :status and pr.inspector.ci = :inspector")})

@Audited
@Data
@Entity
public class PermissionRequest implements BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Enumerated(EnumType.STRING)
  private PermissionRequestStatus permissionRequestStatus;

  @OneToOne(cascade = CascadeType.ALL)
  private Local local;

  @Enumerated(EnumType.STRING)
  private State state;

  @ManyToOne
  private UserAccount inspector;

  @ManyToOne
  private UserAccount economic;

  public PermissionRequest(PermissionRequestStatus permissionRequestStatus, Local local) {
    this.permissionRequestStatus = permissionRequestStatus;
    this.local = local;
    this.state = State.ACTIVE;
  }

  public PermissionRequest() {
  }

  public boolean isToExpireOrExpired() {
    return permissionRequestStatus == PermissionRequestStatus.TO_EXPIRE || permissionRequestStatus == PermissionRequestStatus.EXPIRED;
  }
}
