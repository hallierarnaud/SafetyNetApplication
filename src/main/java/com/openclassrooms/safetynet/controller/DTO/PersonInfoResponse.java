package com.openclassrooms.safetynet.controller.DTO;

import java.util.List;

import lombok.Data;

@Data
public class PersonInfoResponse {

  private String lastName;
  private String address;
  private int age;
  private String email;
  private List<String> medications;
  private List<String> allergies;

}
