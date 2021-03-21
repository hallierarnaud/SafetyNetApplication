package com.openclassrooms.safetynet.model;

import java.util.List;

import javax.persistence.ElementCollection;

import lombok.Data;

@Data
public class PersonMedicalRecordDTO {

  private String firstName;
  private String lastName;
  private String phone;
  private String zip;
  private String address;
  private String city;
  private String email;
  private String birthdate;

  @ElementCollection
  private List<String> medications;

  @ElementCollection
  private List<String> allergies;

}
