package com.openclassrooms.safetynet.domain.object;

import lombok.Data;

@Data
public class Person {

  private Long id;
  private String firstName;
  private String lastName;
  public String phone;
  public String zip;
  public String address;
  public String city;
  public String email;

}
