package com.openclassrooms.safetynet.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "firestations")
public class FireStation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ElementCollection
  private Set<String> addresses = new HashSet<>();
  private String stationNumber;

  public FireStation(String stationNumber) {
    this.stationNumber = stationNumber;
  }

  public FireStation() {

  }

  public FireStation addAddress(String address) {
    addresses.add(address);
    return this;
  }

  /*public String getStationNumber() {
    return stationNumber;
  }*/

  /*public Set<String> getAddresses() {
    return new HashSet<>(addresses);
  }*/

  @Override
  public String toString() {
    return stationNumber.concat(": ") + String.join(", ", addresses);
  }

  /*@OneToMany
  List<Person> persons;*/

}
