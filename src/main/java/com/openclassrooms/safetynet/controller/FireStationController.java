package com.openclassrooms.safetynet.controller;

import com.openclassrooms.safetynet.model.FireStation;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.service.FireStationService;
import com.openclassrooms.safetynet.service.PersonService;

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

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

@RestController
public class FireStationController {

  @Autowired
  private FireStationService fireStationService;

  @GetMapping("/firestations")
  public Iterable<FireStation> getFireStations() {
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
  public void deleteFireStation(@PathVariable("id") long id) {
    fireStationService.deleteFireStation(id);
  }

  @PutMapping("/firestations")
  public ResponseEntity<FireStation> updateFireStation(@RequestBody FireStation fireStation) {
    try {
      return ResponseEntity.ok(fireStationService.updateFireStation(fireStation));
    } catch (EntityNotFoundException e) {
      return ResponseEntity.unprocessableEntity().build();
    }
  }

}
