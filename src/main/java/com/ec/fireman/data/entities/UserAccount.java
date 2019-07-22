package com.ec.fireman.data.entities;

import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.hibernate.envers.Audited;

import javax.persistence.*;

import static com.ec.fireman.data.entities.EntityConstants.GENERIC_COLUMN_SIZE;

@NamedQueries({@NamedQuery(name = "findUserByCi", query = "from UserAccount e where e.ci = :ci")})

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

  @Column(length = 32)
  private String phoneNumber;

  @ManyToOne(cascade = CascadeType.ALL)
  private Role role;

  @Enumerated(EnumType.STRING)
  private State state;

  public UserAccount() {
  }

  public UserAccount(String firstName, String secondName, String firstLastName, String secondLastName, String ci,
                     String password, String email, String phoneNumber, Role role) {
    this.firstName = firstName;
    this.secondName = secondName;
    this.firstLastName = firstLastName;
    this.secondLastName = secondLastName;
    this.setCi(ci);
    this.password = password;
    this.email = email;
    this.role = role;
    this.phoneNumber = phoneNumber;
    state = State.ACTIVE;
  }

  public String getFullName() {
    StringBuilder sb = new StringBuilder(firstName);
    if (StringUtils.isNotEmpty(secondName)) {
      sb.append(" ").append(secondName);
    }
    if (StringUtils.isNotEmpty(firstLastName)) {
      sb.append(" ").append(firstLastName);
    }
    if (StringUtils.isNotEmpty(secondLastName)) {
      sb.append(" ").append(secondLastName);
    }
    return sb.toString();
  }

}
