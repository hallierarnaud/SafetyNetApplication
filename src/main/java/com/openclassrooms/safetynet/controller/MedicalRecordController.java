package com.openclassrooms.safetynet.controller;

import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.service.MedicalRecordService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.NoSuchElementException;

@RestController
public class MedicalRecordController {

  @Autowired
  private MedicalRecordService medicalRecordService;

  @GetMapping("/medicalrecords")
  public Iterable<MedicalRecord> getMedicalRecords() {
    return medicalRecordService.getMedicalRecords();
  }

  @GetMapping("/medicalrecords/{id}")
  public ResponseEntity<MedicalRecord> getMedicalRecordById(@PathVariable("id") long id) {
    try {
      return ResponseEntity.ok(medicalRecordService.getMedicalRecord(id));
    } catch (NoSuchElementException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/medicalrecords")
  public ResponseEntity<Void> addMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
    MedicalRecord medicalRecordAdded = medicalRecordService.saveMedicalRecord(medicalRecord);
    if (medicalRecordAdded == null) {
      return ResponseEntity.noContent().build();
    }
    URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(medicalRecordAdded.getId())
            .toUri();
    return ResponseEntity.created(location).build();
  }

  @DeleteMapping("/medicalrecords/{id}")
  public void deleteMedicalRecord(@PathVariable("id") long id) {
    medicalRecordService.deleteMedicalRecord(id);
  }

  @PutMapping("/medicalrecords")
  public void updateMedicalRecord(@RequestBody MedicalRecord person) {
    medicalRecordService.saveMedicalRecord(person);
  }

}
