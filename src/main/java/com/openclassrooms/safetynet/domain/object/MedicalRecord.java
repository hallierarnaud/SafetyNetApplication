package com.openclassrooms.safetynet.domain.object;

import java.util.List;

import lombok.Data;

@Data
public class MedicalRecord {

  private Long id;
  private String firstName;
  private String lastName;
  private String birthdate;
  private List<String> medications;
  private List<String> allergies;

}
