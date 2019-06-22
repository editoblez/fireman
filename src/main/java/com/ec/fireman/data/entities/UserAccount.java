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

  @ManyToOne(cascade = {CascadeType.MERGE})
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
    this.setCi(ci);
    this.password = password;
    this.email = email;
    this.role = role;
    state = State.ACTIVE;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getSecondName() {
    return secondName;
  }

  public void setSecondName(String secondName) {
    this.secondName = secondName;
  }

  public String getFirstLastName() {
    return firstLastName;
  }

  public void setFirstLastName(String firstLastName) {
    this.firstLastName = firstLastName;
  }

  public String getSecondLastName() {
    return secondLastName;
  }

  public void setSecondLastName(String secondLastName) {
    this.secondLastName = secondLastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public String getPassword() {
    return password;
  }

public String getCi() {
	return ci;
}

public void setCi(String ci) {
	this.ci = ci;
}
}

