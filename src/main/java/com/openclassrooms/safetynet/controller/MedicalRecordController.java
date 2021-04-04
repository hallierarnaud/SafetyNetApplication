package com.openclassrooms.safetynet.controller;

import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.model.MedicalRecordDTO;
import com.openclassrooms.safetynet.service.MapService;
import com.openclassrooms.safetynet.service.MedicalRecordService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import javax.persistence.EntityNotFoundException;

@RestController
public class MedicalRecordController {

  @Autowired
  private MedicalRecordService medicalRecordService;

  @Autowired
  MapService mapService;

  @GetMapping("/medicalrecords")
  public List<MedicalRecordDTO> getMedicalRecords() {
    return medicalRecordService.getMedicalRecords().stream().map(m -> mapService.convertMedicalRecordToMedicalRecordDTO(m)).collect(Collectors.toList());
  }

  @GetMapping("/medicalrecords/{id}")
  public MedicalRecordDTO getMedicalRecordById(@PathVariable("id") long id) {
    try {
      return mapService.convertMedicalRecordToMedicalRecordDTO(medicalRecordService.getMedicalRecord(id));
    } catch (NoSuchElementException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "medicalrecord " + id + " doesn't exist");
    }
  }

  @PostMapping("/medicalrecords")
  public ResponseEntity<MedicalRecord> addMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
    try {
      return ResponseEntity.ok(medicalRecordService.addMedicalRecord(medicalRecord));
    } catch (EntityExistsException e) {
      return ResponseEntity.unprocessableEntity().build();
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
  public MedicalRecordDTO updateMedicalRecord(@PathVariable("id") long id, @RequestBody MedicalRecordDTO medicalRecordDTO) {
    try {
      return mapService.convertMedicalRecordToMedicalRecordDTO(medicalRecordService.updateMedicalRecord(id, medicalRecordDTO));
    } catch (EntityNotFoundException e) {
      throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "medicalrecord " + id + " doesn't exist");
    }
  }

}
