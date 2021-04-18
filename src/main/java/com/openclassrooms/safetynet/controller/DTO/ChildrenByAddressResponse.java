package com.openclassrooms.safetynet.controller.DTO;

import java.util.List;

import lombok.Data;

@Data
public class ChildrenByAddressResponse {

  private List<FirstLastNameAndAgeResponse> childrenByAddressList;
  private List<FirstLastNameAndAgeResponse> adultByAddressList;

}
