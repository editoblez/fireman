package com.ec.fireman.data.entities;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Client implements BaseEntity {
  @Id
  private long id;

  @OneToOne(cascade = CascadeType.ALL)
  @MapsId
  private UserAccount userAccount;

}
