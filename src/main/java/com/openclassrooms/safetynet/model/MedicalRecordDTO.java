package com.openclassrooms.safetynet.model;

import java.util.List;

import lombok.Data;

@Data
public class MedicalRecordDTO {

  private Long id;
  private String birthdate;
  private List<String> medications;
  private List<String> allergies;

}
