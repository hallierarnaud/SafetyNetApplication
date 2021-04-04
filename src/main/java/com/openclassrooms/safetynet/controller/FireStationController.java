package com.openclassrooms.safetynet.controller;

import com.openclassrooms.safetynet.model.FireStation;
import com.openclassrooms.safetynet.service.FireStationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.NoSuchElementException;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

@RestController
public class FireStationController {

  @Autowired
  private FireStationService fireStationService;

  @GetMapping("/firestations")
  public List<FireStation> getFireStations() {
    return fireStationService.getFireStations();
  }

  @GetMapping("/firestations/{id}")
  public ResponseEntity<FireStation> getFireStationById(@PathVariable("id") long id) {
    try {
      return ResponseEntity.ok(fireStationService.getFireStation(id));
    } catch (NoSuchElementException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/firestations")
  public ResponseEntity<FireStation> addFireStation(@RequestBody FireStation fireStation) {
    try {
      return ResponseEntity.ok(fireStationService.addFireStation(fireStation));
    } catch (EntityExistsException e) {
      return ResponseEntity.unprocessableEntity().build();
    }
  }

  @DeleteMapping("/firestations/{id}")
  public ResponseEntity<Void> deleteFireStationById(@PathVariable("id") long id) {
    try {
      fireStationService.deleteFireStation(id);
      return ResponseEntity.ok().build();
    } catch (NoSuchElementException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PutMapping("/firestations/{id}")
  public ResponseEntity<FireStation> updateFireStation(@PathVariable("id") long id, @RequestBody FireStation fireStation) {
    try {
      return ResponseEntity.ok(fireStationService.updateFireStation(id, fireStation));
    } catch (EntityNotFoundException e) {
      return ResponseEntity.unprocessableEntity().build();
    }
  }

}
