package com.openclassrooms.safetynet.service;

import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.repository.PersonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

import javax.persistence.EntityExistsException;

import lombok.Data;

@Data
@Service
public class PersonService {

  @Autowired
  private PersonRepository personRepository;

  public Person getPerson(final Long id) {
    return personRepository.findById(id).orElseThrow(() -> new NoSuchElementException("la personne " + id + " n'existe pas"));
  }

  public Iterable<Person> getPersons() {
    return personRepository.findAll();
  }

  public void deletePerson(final Long id) {
    if (personRepository.existsById(id)) {
      personRepository.deleteById(id);
    }
  }

  public Person savePerson(Person person) {
    return personRepository.save(person);
  }

  public Person addPerson(Person person) {
    if (personRepository.existsById(person.getId())) {
      throw new EntityExistsException("la personne " + person.getId() + " existe déjà");
    }
    return personRepository.save(person);
  }

}
