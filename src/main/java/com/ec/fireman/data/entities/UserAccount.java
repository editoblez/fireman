package com.ec.fireman.data.entities;

import lombok.Data;
import org.hibernate.envers.Audited;

import javax.persistence.*;

import static com.ec.fireman.data.entities.EntityConstants.GENERIC_COLUMN_SIZE;

@NamedQueries({ @NamedQuery(name = "findUserByCi", query = "from UserAccount e where e.ci = :ci") })

@Audited
@Data
@Entity
public class UserAccount implements BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column(length = GENERIC_COLUMN_SIZE, nullable = false)
  private String firstName;

  @Column(length = GENERIC_COLUMN_SIZE)
  private String secondName;

  @Column(length = GENERIC_COLUMN_SIZE, nullable = false)
  private String firstLastName;

  @Column(length = GENERIC_COLUMN_SIZE, nullable = false)
  private String secondLastName;

  @Column(length = GENERIC_COLUMN_SIZE, nullable = false, unique = true)
  private String ci;

  @Column(length = GENERIC_COLUMN_SIZE, nullable = false)
  private String password;

  @Column(length = GENERIC_COLUMN_SIZE)
  private String email;

  @ManyToOne(cascade = CascadeType.ALL)
  private Role role;

  @Enumerated(EnumType.STRING)
  private State state;

  public UserAccount() {
  }

  public UserAccount(String firstName, String secondName, String firstLastName, String secondLastName, String ci,
      String password, String email, Role role) {
    this.firstName = firstName;
    this.secondName = secondName;
    this.firstLastName = firstLastName;
    this.secondLastName = secondLastName;
    this.setCi(ci);
    this.password = password;
    this.email = email;
    this.role = role;
    state = State.ACTIVE;
  }

}
