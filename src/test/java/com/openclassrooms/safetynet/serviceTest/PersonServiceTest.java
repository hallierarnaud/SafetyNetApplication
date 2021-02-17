package com.openclassrooms.safetynet.serviceTest;

import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.repository.PersonRepository;
import com.openclassrooms.safetynet.service.PersonService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

  @Mock
  private PersonRepository personRepository;

  @InjectMocks
  private PersonService personService;

  @Test
  public void addPersonTest_shouldReturnOk () {
    // GIVEN
    Person person = new Person();
    person.setFirstName("Homer");
    when(personRepository.save(any(Person.class))).thenReturn(person);

    // WHEN
    Person created = personService.addPerson(person);

    // THEN
    assertEquals(created.getFirstName(), person.getFirstName());
    verify(personRepository).save(person);
  }

  @Test
  public void getPersons_shouldReturnOk () {
    // GIVEN
    List<Person> persons = new ArrayList();
    persons.add(new Person());
    when(personRepository.findAll()).thenReturn(persons);

    // WHEN
    Iterable<Person> expected = personService.getPersons();

    // THEN
    assertEquals(expected, persons);
    verify(personRepository).findAll();
  }

  @Test
  public void deletePerson_shouldReturnOk () {
    // GIVEN
    Person person = new Person();
    person.setId(1L);
    person.setFirstName("Homer");
    when(personRepository.existsById(person.getId())).thenReturn(TRUE);

    // WHEN
    personService.deletePerson(person.getId());

    // THEN
    verify(personRepository).deleteById(person.getId());
  }

  @Test
  //TODO : Corriger ce test non passant
  //https://gabrielpulga.medium.com/a-beginners-guide-to-unit-testing-crud-endpoints-of-a-spring-boot-java-web-service-api-8ae342c9cbcd
  public void deletePerson_shouldReturnNotFound () {
    // GIVEN
    Person person = new Person();
    person.setId(1L);
    person.setFirstName("Homer");
    when(personRepository.existsById(anyLong())).thenReturn(FALSE);

    // WHEN
    personService.deletePerson(person.getId());

    // THEN
    //verify(personRepository).deleteById(person.getId());
  }

  @Test
  //TODO : Corriger ce test non passant
  public void updatePerson_shouldReturnOk () {
    // GIVEN
    Person person = new Person();
    person.setFirstName("Homer");
    Person newPerson = new Person();
    person.setFirstName("Bart");
    when(personRepository.save(any(Person.class))).thenReturn(person);

    // WHEN
    Person updated = personService.updatePerson(newPerson);

    // THEN
    assertEquals(updated.getFirstName(), person.getFirstName());
    verify(personRepository.save(newPerson));
  }

  @Test
  //TODO : Corriger ce test non passant
  public void updatePerson_shouldReturnNotFound () {
    // GIVEN
    Person person = new Person();
    person.setId(1L);
    person.setFirstName("Homer");
    Person newPerson = new Person();
    newPerson.setId(2L);
    person.setFirstName("Bart");
    when(personRepository.existsById(anyLong())).thenReturn(FALSE);

    // WHEN
    personService.updatePerson(newPerson);
  }

  @Test
  public void getPerson_shouldReturnOk () {
    // GIVEN
    Person person = new Person();
    person.setId(1L);
    when(personRepository.findById(person.getId())).thenReturn(java.util.Optional.of(person));

    // WHEN
    Person expected = personService.getPerson(person.getId());

    // THEN
    assertEquals(expected, person);
    verify(personRepository).findById(person.getId());
  }

  @Test
  //TODO : Corriger ce test non passant
  public void getPerson_shouldReturnNotFound () {
    // GIVEN
    Person person = new Person();
    person.setId(1L);
    person.setFirstName("Homer");
    when(personRepository.findById(anyLong())).thenReturn(null);

    // WHEN
    personService.getPerson(person.getId());

    // THEN

  }

}
