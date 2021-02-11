package com.openclassrooms.safetynet.controller;

import com.openclassrooms.safetynet.model.Person;
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

@RestController
public class PersonController {

  @Autowired
  private PersonService personService;

  @GetMapping("/persons")
  public Iterable<Person> getPersons() {
    return personService.getPersons();
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
  public ResponseEntity<Void> addPerson(@RequestBody Person person) {
    Person personAdded = personService.savePerson(person);
    if (personAdded == null) {
      return ResponseEntity.noContent().build();
    }
    URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(personAdded.getId())
            .toUri();
    return ResponseEntity.created(location).build();
  }

  @DeleteMapping("/persons/{id}")
  public void deletePerson(@PathVariable("id") long id) {
    personService.deletePerson(id);
  }

  @PutMapping("/persons")
  public void updatePerson(@RequestBody Person person) {
    personService.savePerson(person);
  }

}
