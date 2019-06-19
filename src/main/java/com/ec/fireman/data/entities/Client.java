package com.ec.fireman.data.entities;

import javax.persistence.*;

@Entity
public class Client implements BaseEntity {
  @Id
  private long id;

  @OneToOne(cascade = CascadeType.ALL)
  @MapsId
  private UserAccount userAccount;

}
