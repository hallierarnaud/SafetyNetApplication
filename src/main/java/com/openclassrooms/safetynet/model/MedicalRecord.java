package com.openclassrooms.safetynet.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "medicalrecords")

public class MedicalRecord {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String birthdate;

  @ElementCollection
  private List<String> medications;

  @ElementCollection
  private List<String> allergies;

  @OneToOne
  @JsonBackReference
  private Person person;

}
