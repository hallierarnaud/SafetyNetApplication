package com.openclassrooms.safetynet.model;

import lombok.Data;

@Data
public class PersonDTO {

  private Long id;
  private String firstName;
  private String lastName;
  private String phone;
  private String zip;
  private String address;
  private String city;
  private String email;

}
