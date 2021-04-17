package com.openclassrooms.safetynet.controller.DTO;

import java.util.List;

import lombok.Data;

@Data
public class PersonByFireStationResponse {

  private List<ShortPersonResponse> personsByFireStation;
  private int minorNumber;
  private int majorNumber;

}
