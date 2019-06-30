package com.ec.fireman.data.entities;

import javax.persistence.Embeddable;

@Embeddable
public class CommonField {
  private long createdDate;
  private long lastModifiedDate;
}
