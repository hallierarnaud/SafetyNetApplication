package com.openclassrooms.safetynet.model;

import java.util.List;

import lombok.Data;

@Data
public class MedicalRecordDTO {

  private String birthdate;
  private List<String> medications;
  private List<String> allergies;

}
