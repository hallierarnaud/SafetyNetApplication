package com.openclassrooms.safetynet.controller.endpoint;

import com.openclassrooms.safetynet.controller.DTO.MedicalRecordAddOrUpdateRequest;
import com.openclassrooms.safetynet.controller.DTO.MedicalRecordResponse;
import com.openclassrooms.safetynet.domain.service.MapService;
import com.openclassrooms.safetynet.domain.service.MedicalRecordService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.persistence.EntityExistsException;

@RestController
public class MedicalRecordController {

  @Autowired
  private MedicalRecordService medicalRecordService;

  @Autowired
  MapService mapService;

  @GetMapping("/medicalrecords")
  public List<MedicalRecordResponse> getMedicalRecords() {
    return medicalRecordService.getMedicalRecords().stream().map(m -> mapService.convertMedicalRecordToMedicalRecordResponse(m)).collect(Collectors.toList());
  }

  @GetMapping("/medicalrecords/{id}")
  public MedicalRecordResponse getMedicalRecordById(@PathVariable("id") long id) {
    try {
      return mapService.convertMedicalRecordToMedicalRecordResponse(medicalRecordService.getMedicalRecord(id));
    } catch (NoSuchElementException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "medicalrecord " + id + " doesn't exist");
    }
  }

  @PostMapping("/medicalrecords")
  public MedicalRecordResponse addMedicalRecord(@RequestBody MedicalRecordAddOrUpdateRequest medicalRecordAddRequest) {
    try {
      return mapService.convertMedicalRecordToMedicalRecordResponse(medicalRecordService.addMedicalRecord(medicalRecordAddRequest));
    } catch (EntityExistsException e) {
      throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "medicalrecord " + medicalRecordAddRequest.getId() + " already exists");
    } catch (NoSuchElementException e) {
      throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "person " + medicalRecordAddRequest.getId() + " doesn't exist");
    }
  }

  @DeleteMapping("/medicalrecords/{id}")
  public void deleteMedicalRecordById(@PathVariable("id") long id) {
    try {
      medicalRecordService.deleteMedicalRecord(id);
      throw new ResponseStatusException(HttpStatus.OK, "medicalrecord " + id + " was deleted");
    } catch (NoSuchElementException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "medicalrecord " + id + " doesn't exist");
    }
  }

  @PutMapping("/medicalrecords/{id}")
  public MedicalRecordResponse updateMedicalRecord(@PathVariable("id") long id, @RequestBody MedicalRecordAddOrUpdateRequest medicalRecordUpdateRequest) {
    try {
      return mapService.convertMedicalRecordToMedicalRecordResponse(medicalRecordService.updateMedicalRecord(id, medicalRecordUpdateRequest));
    } catch (NoSuchElementException e) {
      throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "medicalrecord " + id + " doesn't exist");
    }
  }

}
