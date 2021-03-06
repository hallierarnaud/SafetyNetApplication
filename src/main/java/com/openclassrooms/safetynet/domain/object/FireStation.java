package com.openclassrooms.safetynet.domain.object;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;

@Data
public class FireStation {

  private Long id;
  private Set<String> addresses = new HashSet<>();
  private String stationNumber;

}
