package com.openclassrooms.safetynet.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "medicalrecords")

public class MedicalRecord {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String firstName;

  @Column
  private String lastName;

  @Column
  private String birthdate;

  @OneToMany
  private Set<Medication> medications = new HashSet<Medication>();

  public void addMedication(Medication medication) {
    medications.add(medication);
  }
  @OneToMany
  private Set<Allergy> allergies = new HashSet<Allergy>();

  public void addAllergy(Allergy allergy) {
    allergies.add(allergy);
  }

}
