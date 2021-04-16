package com.openclassrooms.safetynet.controller.DTO;

import lombok.Data;

@Data
public class PersonByFireStationResponse {

  private String firstName;
  private String lastName;
  private String address;
  private String phone;

}
