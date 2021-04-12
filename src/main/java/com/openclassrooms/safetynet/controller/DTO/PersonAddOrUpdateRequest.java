package com.openclassrooms.safetynet.controller.DTO;

import lombok.Data;

@Data
public class PersonAddOrUpdateRequest {

  private Long id;
  private String firstName;
  private String lastName;
  private String phone;
  private String zip;
  private String address;
  private String city;
  private String email;

}
