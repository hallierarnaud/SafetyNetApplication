package com.openclassrooms.safetynet.controller.DTO;

import java.util.List;

import lombok.Data;

@Data
public class MedicalRecordResponse {

  private Long id;
  private String birthdate;
  private List<String> medications;
  private List<String> allergies;

}
