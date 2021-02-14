package com.openclassrooms.safetynet.service;

import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.repository.PersonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import lombok.Data;

@Data
@Service
public class PersonService {

  @Autowired
  private PersonRepository personRepository;

  public Person getPerson(final Long id) {
    return personRepository.findById(id).orElseThrow(() -> new NoSuchElementException("person " + id + " already exists"));
  }

  public Iterable<Person> getPersons() {
    return personRepository.findAll();
  }

  public void deletePerson(final Long id) {
    if (personRepository.existsById(id)) {
      personRepository.deleteById(id);
    }
  }

  public Person updatePerson(Person person) {
    if (!personRepository.existsById(person.getId())) {
      throw new EntityNotFoundException("person " + person.getId() + " doesn't exist");
    }
    return personRepository.save(person);
  }

  public Person addPerson(Person person) {
    if (personRepository.existsById(person.getId())) {
      throw new EntityExistsException("person " + person.getId() + " already exists");
    }
    return personRepository.save(person);
  }

}
