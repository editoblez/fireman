package com.ec.fireman.data.entities;

import lombok.Data;

import javax.persistence.*;

import static com.ec.fireman.data.entities.EntityConstants.GENERIC_COLUMN_SIZE;

@NamedQueries({
    @NamedQuery(
        name = "findUserByCi",
        query = "from UserAccount e where e.ci = :ci"
    )
})
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

  @Column(length = GENERIC_COLUMN_SIZE, nullable = false)
  private String ci;

  @Column(length = GENERIC_COLUMN_SIZE, nullable = false)
  private String password;

  @Column(unique = true, length = GENERIC_COLUMN_SIZE, nullable = false)
  private String email;

  @ManyToOne(cascade = CascadeType.ALL)
  private Role role;

  @Enumerated(EnumType.STRING)
  private State state;

  public UserAccount() {
  }

  public UserAccount(String firstName, String secondName, String firstLastName, String secondLastName, String ci, String password, String email, Role role) {
    this.firstName = firstName;
    this.secondName = secondName;
    this.firstLastName = firstLastName;
    this.secondLastName = secondLastName;
    this.ci = ci;
    this.password = password;
    this.email = email;
    this.role = role;
    state = State.ACTIVE;
  }

}

