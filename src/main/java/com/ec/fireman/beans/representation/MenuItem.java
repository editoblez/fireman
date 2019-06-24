package com.ec.fireman.beans.representation;

import lombok.Data;

@Data
public class MenuItem {
  private String name;
  private String uri;
  private boolean rendered;

  public MenuItem(String name, String uri, boolean rendered) {
    this.name = name;
    this.uri = uri;
    this.rendered = rendered;
  }
}
