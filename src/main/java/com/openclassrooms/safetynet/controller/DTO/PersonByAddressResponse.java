package com.openclassrooms.safetynet.controller.DTO;

import java.util.List;

import lombok.Data;

@Data
public class PersonByAddressResponse {

  private List<NamePhoneAgeAndMedicalRecordResponse> namePhoneAgeAndMedicalRecordResponseList;
  private String stationNumber;

}
