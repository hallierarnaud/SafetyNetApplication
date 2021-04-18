package com.openclassrooms.safetynet.controller.DTO;

import java.util.List;

import lombok.Data;

@Data
public class ChildrenByAddressResponse {

  private List<FirstAndLastNameResponse> childrenByAddress;
  private List<FirstAndLastNameResponse> adultByAddress;

}
