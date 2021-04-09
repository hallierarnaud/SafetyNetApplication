package com.openclassrooms.safetynet.controller.DTO;

import java.util.List;

import lombok.Data;

@Data
public class PersonMedicalRecordResponse {

  private Long id;
  private String firstName;
  private String lastName;
  private String phone;
  private String zip;
  private String address;
  private String city;
  private String email;
  private String birthdate;
  private List<String> medications;
  private List<String> allergies;

}
