package com.ec.fireman.data.entities;

import javax.persistence.*;
import java.util.List;

import static com.ec.fireman.data.entities.EntityConstants.GENERIC_COLUMN_SIZE;

@Entity
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column(unique = true, length = GENERIC_COLUMN_SIZE, nullable = false)
  private String roleName;
  @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
  private List<UserAccount> userAccounts;

  public Role() {
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  public List<UserAccount> getUserAccounts() {
    return userAccounts;
  }

  public void setUserAccounts(List<UserAccount> userAccounts) {
    this.userAccounts = userAccounts;
  }
}

