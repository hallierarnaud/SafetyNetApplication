package com.openclassrooms.safetynet.controller;

import com.openclassrooms.safetynet.model.PersonMedicalRecordDTO;
import com.openclassrooms.safetynet.service.MapService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MapController {

  @Autowired
  private MapService mapService;

  @GetMapping("/map")
  @ResponseBody
  public List<PersonMedicalRecordDTO> getAllPersonsMedicalRecord() {
    List<PersonMedicalRecordDTO> personsMedicalRecord = mapService.getAllPersonsMedicalRecord();
    return personsMedicalRecord;
  }

}
