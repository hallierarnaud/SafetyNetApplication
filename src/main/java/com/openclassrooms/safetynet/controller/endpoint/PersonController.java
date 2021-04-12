package com.openclassrooms.safetynet.controller.endpoint;

import com.openclassrooms.safetynet.controller.DTO.PersonMedicalRecordResponse;
import com.openclassrooms.safetynet.controller.DTO.PersonResponse;
import com.openclassrooms.safetynet.controller.DTO.PersonAddOrUpdateRequest;
import com.openclassrooms.safetynet.domain.service.MapService;
import com.openclassrooms.safetynet.domain.service.PersonService;
import com.openclassrooms.safetynet.model.entity.FireStationEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.persistence.EntityExistsException;

@RestController
public class PersonController {

  @Autowired
  private PersonService personService;

  @Autowired
  private MapService mapService;

  @GetMapping("/persons")
  public List<PersonResponse> getPersons() {
    return personService.getPersons().stream().map(p -> mapService.convertPersonToPersonResponse(p)).collect(Collectors.toList());
  }

  @GetMapping("/persons/{id}")
  public PersonResponse getPersonById(@PathVariable("id") long id) {
    try {
      return mapService.convertPersonToPersonResponse(personService.getPerson(id));
    } catch (NoSuchElementException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "person " + id + " doesn't exist");
    }
  }

  /*@PostMapping("/persons")
  public PersonMedicalRecordResponse addPerson(@RequestBody PersonMedicalRecordResponse personMedicalRecordResponse) {
    try {
      return mapService.convertPersonToPersonMedicalRecordDTO(personService.addPerson(personMedicalRecordResponse));
    } catch (EntityExistsException e) {
      throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "person " + personMedicalRecordResponse.getId() + " already exists");
    }
  }*/

  @PostMapping("/persons")
  public PersonResponse addSimplePerson(@RequestBody PersonAddOrUpdateRequest personAddRequest) {
    try {
      return mapService.convertPersonToPersonResponse(personService.addSimplePerson(personAddRequest));
    } catch (EntityExistsException e) {
      throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "person " + personAddRequest.getId() + " already exists");
    }
  }

  @DeleteMapping("/persons/{id}")
  public void deletePersonById(@PathVariable("id") long id) {
    try {
      personService.deletePerson(id);
      throw new ResponseStatusException(HttpStatus.OK, "person " + id + " was deleted");
    } catch (NoSuchElementException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "person " + id + " doesn't exist");
    }
  }

  /*@PutMapping("/persons/{id}")
  public PersonMedicalRecordResponse updatePerson(@PathVariable("id") long id, @RequestBody PersonUpdateRequest personUpdateRequest) {
    try {
      return mapService.convertPersonToPersonMedicalRecordDTO(personService.updatePerson(id, personUpdateRequest));
    } catch (EntityNotFoundException e) {
      throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "person " + id + " doesn't exist");
    }
  }*/

  @PutMapping("/persons/{id}")
  public PersonResponse updateSimplePerson(@PathVariable("id") long id, @RequestBody PersonAddOrUpdateRequest personUpdateRequest) {
    try {
      return mapService.convertPersonToPersonResponse(personService.updateSimplePerson(id, personUpdateRequest));
    } catch (NoSuchElementException e) {
      throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "person " + id + " doesn't exist");
    }
  }

  @GetMapping("/persons/name")
  public List<PersonMedicalRecordResponse> getPersonByLastName(@RequestParam("lastName") String lastName) {
      return personService.findByLastNameLike(lastName).stream().map(p -> mapService.convertPersonToPersonMedicalRecordDTO(p)).collect(Collectors.toList());
  }

  @GetMapping("/persons/firestations/{id}")
  public FireStationEntity getPersonFireStation(@PathVariable("id") long id) {
    try {
      return personService.getPersonFireStation(id);
    } catch (NoSuchElementException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "person " + id + " doesn't exist or hasn't a firestation");
    }
  }

}
