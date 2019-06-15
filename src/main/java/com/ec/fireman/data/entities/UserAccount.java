package com.ec.fireman.data.entities;

import javax.persistence.*;

import static com.ec.fireman.data.entities.EntityConstants.GENERIC_COLUMN_SIZE;

@Entity
public class UserAccount {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column(unique = true, length = GENERIC_COLUMN_SIZE, nullable = false)
  private String firstName;

  @Column(unique = true, length = GENERIC_COLUMN_SIZE)
  private String secondName;

  @Column(unique = true, length = GENERIC_COLUMN_SIZE, nullable = false)
  private String firstLastName;

  @Column(unique = true, length = GENERIC_COLUMN_SIZE, nullable = false)
  private String secondLastName;

  @Column(unique = true, length = GENERIC_COLUMN_SIZE, nullable = false)
  private String email;

  @ManyToOne
  private Role role;

  public UserAccount() {
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
}

