package com.openclassrooms.safetynet.controller;

import com.openclassrooms.safetynet.model.FireStation;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.model.PersonMedicalRecordDTO;
import com.openclassrooms.safetynet.service.MapService;
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

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

@RestController
public class PersonController {

  @Autowired
  private PersonService personService;

  @Autowired
  private MapService mapService;

  @GetMapping("/persons")
  public List<PersonMedicalRecordDTO> getPersons() {
    return personService.getPersons().stream().map(p -> mapService.convertToPersonMedicalRecordDTO(p)).collect(Collectors.toList());
  }

  @GetMapping("/persons/{id}")
  public ResponseEntity<Person> getPersonById(@PathVariable("id") long id) {
    try {
      return ResponseEntity.ok(personService.getPerson(id));
    } catch (NoSuchElementException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping("/persons")
  public ResponseEntity<Person> addPerson(@RequestBody Person person) {
    try {
      return ResponseEntity.ok(personService.addPerson(person));
    } catch (EntityExistsException e) {
      return ResponseEntity.unprocessableEntity().build();
    }
  }

  @DeleteMapping("/persons/{id}")
  public ResponseEntity<Void> deletePersonById(@PathVariable("id") long id) {
    try {
      personService.deletePerson(id);
      return ResponseEntity.ok().build();
    } catch (NoSuchElementException e) {
      return ResponseEntity.notFound().build();
    }
  }

  @PutMapping("/persons/{id}")
  public ResponseEntity<Person> updatePerson(@PathVariable("id") long id, @RequestBody Person person) {
    try {
      return ResponseEntity.ok(personService.updatePerson(id, person));
    } catch (EntityNotFoundException e) {
      return ResponseEntity.unprocessableEntity().build();
    }
  }

  @GetMapping("/persons/name/{lastName}")
  public Iterable<Person> getPersonByLastName(@PathVariable("lastName") String lastName) {
      return personService.findByNomLike(lastName);
  }

  @GetMapping("/persons/firestations/{id}")
  public ResponseEntity<FireStation> getPersonFireStation(@PathVariable("id") long id) {
    try {
      return ResponseEntity.ok(personService.getPersonFireStation(id));
    } catch (NoSuchElementException e) {
      return ResponseEntity.notFound().build();
    }
  }

}
