package com.openclassrooms.safetynet.controller.endpoint;

import com.openclassrooms.safetynet.model.entity.FireStationEntity;
import com.openclassrooms.safetynet.domain.service.FireStationService;

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

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

@RestController
public class FireStationController {

  @Autowired
  private FireStationService fireStationService;

  @GetMapping("/firestations")
  public List<FireStationEntity> getFireStations() {
    return fireStationService.getFireStations();
  }

  @GetMapping("/firestations/{id}")
  public FireStationEntity getFireStationById(@PathVariable("id") long id) {
    try {
      return fireStationService.getFireStation(id);
    } catch (NoSuchElementException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "firestation " + id + " doesn't exist");
    }
  }

  @PostMapping("/firestations")
  public FireStationEntity addFireStation(@RequestBody FireStationEntity fireStation) {
    try {
      return fireStationService.addFireStation(fireStation);
    } catch (EntityExistsException e) {
      throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "firestation " + fireStation.getId() + " already exists");
    }
  }

  @DeleteMapping("/firestations/{id}")
  public void deleteFireStationById(@PathVariable("id") long id) {
    try {
      fireStationService.deleteFireStation(id);
      throw new ResponseStatusException(HttpStatus.OK, "firestation " + id + " was deleted");
    } catch (NoSuchElementException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "firestation " + id + " doesn't exist");
    }
  }

  @PutMapping("/firestations/{id}")
  public FireStationEntity updateFireStation(@PathVariable("id") long id, @RequestBody FireStationEntity fireStation) {
    try {
      return fireStationService.updateFireStation(id, fireStation);
    } catch (EntityNotFoundException e) {
      throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "firestation " + id + " doesn't exist");
    }
  }

}
