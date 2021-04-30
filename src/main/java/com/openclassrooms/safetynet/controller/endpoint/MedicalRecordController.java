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

/**
 * a class to perform CRUD operations on medical record
 */
@RestController
public class MedicalRecordController {

  @Autowired
  private MedicalRecordService medicalRecordService;

  @Autowired
  MapService mapService;

  /**
   * @return a list of all medical records in the database
   */
  @GetMapping("/medicalrecords")
  public List<MedicalRecordResponse> getMedicalRecords() {
    return medicalRecordService.getMedicalRecords().stream().map(m -> mapService.convertMedicalRecordToMedicalRecordResponse(m)).collect(Collectors.toList());
  }

  /**
   * @param id a medical record's id
   * @return a medical record corresponding to the id
   */
  @GetMapping("/medicalrecords/{id}")
  public MedicalRecordResponse getMedicalRecordById(@PathVariable("id") long id) {
    try {
      return mapService.convertMedicalRecordToMedicalRecordResponse(medicalRecordService.getMedicalRecord(id));
    } catch (NoSuchElementException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "medicalrecord " + id + " doesn't exist");
    }
  }

  /**
   * @param medicalRecordAddRequest a medical record defined by his attributes
   * @return add the medical record to the database
   */
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

  /**
   * @param firstName first name of a person
   * @param lastName last name of a person
   */
  @DeleteMapping("/medicalrecords/{firstName}/{lastName}")
  public void deleteMedicalRecordByFirstAndLastName(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName) {
    try {
      medicalRecordService.deleteMedicalRecord(firstName, lastName);
      throw new ResponseStatusException(HttpStatus.OK, "medicalrecord of " + firstName + " " + lastName + " was deleted");
    } catch (NoSuchElementException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "medicalrecord of " + firstName + " " + lastName + " doesn't exist");
    }
  }

  /**
   * @param id a medical record's id
   * @param medicalRecordUpdateRequest a medical record defined by his attributes
   * @return update the medical record in the database
   */
  @PutMapping("/medicalrecords/{id}")
  public MedicalRecordResponse updateMedicalRecord(@PathVariable("id") long id, @RequestBody MedicalRecordAddOrUpdateRequest medicalRecordUpdateRequest) {
    try {
      return mapService.convertMedicalRecordToMedicalRecordResponse(medicalRecordService.updateMedicalRecord(id, medicalRecordUpdateRequest));
    } catch (NoSuchElementException e) {
      throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "medicalrecord " + id + " doesn't exist");
    }
  }

}
