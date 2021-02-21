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

import java.util.NoSuchElementException;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

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
  public ResponseEntity<MedicalRecord> addMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
    try {
      return ResponseEntity.ok(medicalRecordService.addMedicalRecord(medicalRecord));
    } catch (EntityExistsException e) {
      return ResponseEntity.unprocessableEntity().build();
    }
  }

  @DeleteMapping("/medicalrecords/{id}")
  public ResponseEntity<Void> deleteMedicalRecordById(@PathVariable("id") long id) {
    try {
      medicalRecordService.deleteMedicalRecord(id);
      return ResponseEntity.ok().build();
    } catch (NoSuchElementException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PutMapping("/medicalrecords/{id}")
  public ResponseEntity<MedicalRecord> updateMedicalRecord(@PathVariable("id") long id, @RequestBody MedicalRecord medicalRecord) {
    try {
      return ResponseEntity.ok(medicalRecordService.updateMedicalRecord(id, medicalRecord));
    } catch (EntityNotFoundException e) {
      return ResponseEntity.unprocessableEntity().build();
    }
  }

}
