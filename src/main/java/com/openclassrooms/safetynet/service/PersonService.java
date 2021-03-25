package com.openclassrooms.safetynet.service;

import com.openclassrooms.safetynet.model.FireStation;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.repository.FireStationRepository;
import com.openclassrooms.safetynet.repository.PersonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import lombok.Data;

@Data
@Service
public class PersonService {

  @Autowired
  private PersonRepository personRepository;

  @Autowired
  private FireStationRepository fireStationRepository;

  public Person getPerson(final Long id) {
    return personRepository.findById(id).orElseThrow(() -> new NoSuchElementException("person " + id + " doesn't exist"));
  }

  public Iterable<Person> getPersons() {
    return personRepository.findAll();
  }

  public void deletePerson(final Long id) {
    if (!personRepository.existsById(id)) {
      throw new NoSuchElementException("person " + id + " doesn't exist");
    }
    personRepository.deleteById(id);
  }

  public Person updatePerson(final Long id, Person person) {
    if (!personRepository.existsById(id)) {
      throw new EntityNotFoundException("person " + id + " doesn't exist");
    }
    return personRepository.save(person);
  }

  public Person addPerson(Person person) {
    if (personRepository.existsById(person.getId())) {
      throw new EntityExistsException("person " + person.getId() + " already exists");
    }
    return personRepository.save(person);
  }

  public Iterable<Person> findByNomLike(String lastName) {
    return personRepository.findPersonByLastName(lastName);
  }

  public Optional<FireStation> getPersonFireStation(Long id) {
    return fireStationRepository.findById(id);
  }

}
