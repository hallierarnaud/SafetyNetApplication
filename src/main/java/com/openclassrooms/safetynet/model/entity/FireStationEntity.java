package com.openclassrooms.safetynet.model.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "firestations")
public class FireStationEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ElementCollection
  private Set<String> addresses = new HashSet<>();
  private String stationNumber;

  public FireStationEntity(String stationNumber) {
    this.stationNumber = stationNumber;
  }

  public FireStationEntity addAddress(String address) {
    addresses.add(address);
    return this;
  }

  @Override
  public String toString() {
    return stationNumber.concat(": ") + String.join(", ", addresses);
  }

}
