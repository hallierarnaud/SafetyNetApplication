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

  public List<Person> findByLastName(String lastName) {
    List<PersonEntity> personEntities = personRepository.findByLastName(lastName);
    return personEntities.stream().map((entity) -> {
      Person person = new Person();
      person.setFirstName(entity.getFirstName());
      person.setLastName(entity.getLastName());
      return person;
    }).collect(Collectors.toList());
  }

  public Person findById(Long id) {
    PersonEntity entity = personRepository.findById(id).orElseThrow(() -> new NoSuchElementException(""));
    Person person = new Person();
    person.setFirstName(entity.getFirstName());
    person.setLastName(entity.getLastName());
    person.setPhone(entity.getPhone());
    person.setZip(entity.getZip());
    person.setAddress(entity.getAddress());
    person.setCity(entity.getCity());
    person.setEmail(entity.getEmail());
    return person;
  }

  /*public Person update(Person person) {
    // TODO : doit faire la transformation inverse entre entity et person
    return person;
  }*/

  public Person updateSimplePerson(Person person) {
    PersonEntity entity = personRepository.findById(person.getId()).orElseThrow(() -> new NoSuchElementException(""));
    entity.setFirstName(person.getFirstName());
    entity.setLastName(person.getLastName());
    entity.setPhone(person.getPhone());
    entity.setZip(person.getZip());
    entity.setAddress(person.getAddress());
    entity.setCity(person.getCity());
    entity.setEmail(person.getEmail());
    personRepository.save(entity);
    return person;
  }

  public List<Person> findAll() {
    List<PersonEntity> personEntities =  StreamSupport.stream(personRepository.findAll().spliterator(),false)
            .collect(Collectors.toList());
    return personEntities.stream().map((entity) -> {
      Person person = new Person();
      person.setId(entity.getId());
      person.setFirstName(entity.getFirstName());
      person.setLastName(entity.getLastName());
      person.setPhone(entity.getPhone());
      person.setZip(entity.getZip());
      person.setAddress(entity.getAddress());
      person.setCity(entity.getCity());
      person.setEmail(entity.getEmail());
      return person;
    }).collect(Collectors.toList());
  }

}
