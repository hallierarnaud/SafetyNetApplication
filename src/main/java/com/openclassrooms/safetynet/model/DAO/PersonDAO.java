package com.openclassrooms.safetynet.model.DAO;

import com.openclassrooms.safetynet.domain.object.Person;
import com.openclassrooms.safetynet.model.entity.PersonEntity;
import com.openclassrooms.safetynet.model.repository.PersonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Component
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
    return person;
  }

  public Person update(Person person) {
    // TODO : doit faire la transformation inverse entre entity et person
    return person;
  }
}
