package com.ec.fireman.beans;

import javax.annotation.ManagedBean;

@ManagedBean("helloWorld")
public class HelloWorld {

  public HelloWorld() {
    System.out.println("HelloWorld started!");
  }

  public String getMessage() {
    return "Hello World!";
  }
}
