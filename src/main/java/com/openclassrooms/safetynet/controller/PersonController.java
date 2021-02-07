package com.openclassrooms.safetynet.controller;

import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

  @PostMapping("/add")
  public String addPerson(@RequestParam String firstName, @RequestParam String lastName, @RequestParam String address, @RequestParam String city, @RequestParam String zip, @RequestParam String phone, @RequestParam String email) {
    Person person = new Person();
    person.setFirstName(firstName);
    person.setLastName(lastName);
    person.setAddress(address);
    person.setCity(city);
    person.setZip(zip);
    person.setPhone(phone);
    person.setEmail(email);
    personService.savePerson(person);
    return "Added new person to repo !";
  }

}
