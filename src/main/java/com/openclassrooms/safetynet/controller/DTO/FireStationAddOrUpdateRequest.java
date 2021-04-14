package com.openclassrooms.safetynet.controller.DTO;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;

@Data
public class FireStationAddOrUpdateRequest {

  private Long id;
  private Set<String> addresses = new HashSet<>();
  private String stationNumber;

}
