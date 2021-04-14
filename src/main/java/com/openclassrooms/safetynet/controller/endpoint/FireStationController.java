package com.openclassrooms.safetynet.controller.endpoint;

import com.openclassrooms.safetynet.controller.DTO.FireStationAddOrUpdateRequest;
import com.openclassrooms.safetynet.controller.DTO.FireStationResponse;
import com.openclassrooms.safetynet.domain.service.FireStationService;
import com.openclassrooms.safetynet.domain.service.MapService;

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
public class FireStationController {

  @Autowired
  private FireStationService fireStationService;

  @Autowired
  MapService mapService;

  @GetMapping("/firestations")
  public List<FireStationResponse> getFireStations() {
    return fireStationService.getFireStations().stream().map(f -> mapService.convertFireStationToFireStationResponse(f)).collect(Collectors.toList());
  }

  @GetMapping("/firestations/{id}")
  public FireStationResponse getFireStationById(@PathVariable("id") long id) {
    try {
      return mapService.convertFireStationToFireStationResponse(fireStationService.getFireStation(id));
    } catch (NoSuchElementException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "firestation " + id + " doesn't exist");
    }
  }

  @PostMapping("/firestations")
  public FireStationResponse addFireStation(@RequestBody FireStationAddOrUpdateRequest fireStationAddRequest) {
    try {
      return mapService.convertFireStationToFireStationResponse(fireStationService.addFireStation(fireStationAddRequest));
    } catch (EntityExistsException e) {
      throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "firestation " + fireStationAddRequest.getId() + " already exists");
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
  public FireStationResponse updateFireStation(@PathVariable("id") long id, @RequestBody FireStationAddOrUpdateRequest fireStationUpdateRequest) {
    try {
      return mapService.convertFireStationToFireStationResponse(fireStationService.updateFireStation(id, fireStationUpdateRequest));
    } catch (NoSuchElementException e) {
      throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "firestation " + id + " doesn't exist");
    }
  }

}
