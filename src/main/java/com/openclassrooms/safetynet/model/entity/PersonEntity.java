package com.openclassrooms.safetynet.model.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "persons")
public class PersonEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  public String firstName;
  public String lastName;
  public String phone;
  public String zip;
  public String address;
  public String city;
  public String email;

  @OneToOne(fetch = FetchType.LAZY, mappedBy = "person", orphanRemoval = true, cascade = CascadeType.ALL)
  MedicalRecordEntity medicalRecord;

}
