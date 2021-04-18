package com.openclassrooms.safetynet.controller.DTO;

import java.util.List;

import lombok.Data;

@Data
public class NamePhoneAgeAndMedicalRecordResponse {

  private String lastName;
  private String phone;
  private int age;
  private List<String> medications;
  private List<String> allergies;

}
