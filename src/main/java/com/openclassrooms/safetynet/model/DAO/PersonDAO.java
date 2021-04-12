package com.openclassrooms.safetynet.model.DAO;

import com.openclassrooms.safetynet.domain.object.Person;
import com.openclassrooms.safetynet.model.entity.PersonEntity;
import com.openclassrooms.safetynet.model.repository.PersonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public class PersonDAO {

  @Autowired
  private PersonRepository personRepository;

  public Person findById(Long id) {
    PersonEntity personEntity = personRepository.findById(id).orElseThrow(() -> new NoSuchElementException("person " + id + " doesn't exist"));
    Person person = new Person();
    person.setId(personEntity.getId());
    person.setFirstName(personEntity.getFirstName());
    person.setLastName(personEntity.getLastName());
    person.setPhone(personEntity.getPhone());
    person.setZip(personEntity.getZip());
    person.setAddress(personEntity.getAddress());
    person.setCity(personEntity.getCity());
    person.setEmail(personEntity.getEmail());
    return person;
  }

  public List<Person> findAll() {
    List<PersonEntity> personEntities =  StreamSupport.stream(personRepository.findAll().spliterator(),false)
            .collect(Collectors.toList());
    return personEntities.stream().map((personEntity) -> {
      Person person = new Person();
      person.setId(personEntity.getId());
      person.setFirstName(personEntity.getFirstName());
      person.setLastName(personEntity.getLastName());
      person.setPhone(personEntity.getPhone());
      person.setZip(personEntity.getZip());
      person.setAddress(personEntity.getAddress());
      person.setCity(personEntity.getCity());
      person.setEmail(personEntity.getEmail());
      return person;
    }).collect(Collectors.toList());
  }

  public Person updateSimplePerson(Long id, Person person) {
    PersonEntity personEntity = personRepository.findById(id).orElseThrow(() -> new NoSuchElementException("person " + id + " doesn't exist"));
    personEntity.setFirstName(person.getFirstName());
    personEntity.setLastName(person.getLastName());
    personEntity.setPhone(person.getPhone());
    personEntity.setZip(person.getZip());
    personEntity.setAddress(person.getAddress());
    personEntity.setCity(person.getCity());
    personEntity.setEmail(person.getEmail());
    personRepository.save(personEntity);
    return person;
  }

  public Person addSimplePerson(Person person) {
    PersonEntity personEntity = new PersonEntity();
    personEntity.setFirstName(person.getFirstName());
    personEntity.setLastName(person.getLastName());
    personEntity.setPhone(person.getPhone());
    personEntity.setZip(person.getZip());
    personEntity.setAddress(person.getAddress());
    personEntity.setCity(person.getCity());
    personEntity.setEmail(person.getEmail());
    personRepository.save(personEntity);
    return person;
  }

  public Boolean existById(Long id) {
    return personRepository.existsById(id);
  }

  public void deleteById(Long id) {
    personRepository.deleteById(id);
  }

  public List<Person> findByLastName(String lastName) {
    List<PersonEntity> personEntities = personRepository.findByLastName(lastName);
    return personEntities.stream().map((personEntity) -> {
      Person person = new Person();
      person.setFirstName(personEntity.getFirstName());
      person.setLastName(personEntity.getLastName());
      return person;
    }).collect(Collectors.toList());
  }

}
