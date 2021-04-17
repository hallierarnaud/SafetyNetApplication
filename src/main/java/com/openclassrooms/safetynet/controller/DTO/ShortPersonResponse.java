package com.openclassrooms.safetynet.controller.DTO;

import lombok.Data;

@Data
public class ShortPersonResponse {

  private String firstName;
  private String lastName;
  private String address;
  private String phone;

}
