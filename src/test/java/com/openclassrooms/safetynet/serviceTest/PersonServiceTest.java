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
import java.util.NoSuchElementException;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
  public void addPersonTest_shouldReturnAlreadyExist () {
    // GIVEN
    Person person = new Person();
    person.setId(1L);

    // WHEN
    when(personRepository.existsById(anyLong())).thenReturn(TRUE);

    // THEN
    Throwable exception = assertThrows(EntityExistsException.class, () -> personService.addPerson(person));
    assertEquals("person 1 already exists", exception.getMessage());
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
    when(personRepository.existsById(anyLong())).thenReturn(TRUE);

    // WHEN
    personService.deletePerson(person.getId());

    // THEN
    verify(personRepository).deleteById(person.getId());
  }

  @Test
  public void deletePerson_shouldReturnNotFound () {
    // GIVEN
    Person person = new Person();
    person.setId(1L);

    // WHEN
    when(personRepository.existsById(anyLong())).thenReturn(FALSE);

    // THEN
    Throwable exception = assertThrows(NoSuchElementException.class, () -> personService.deletePerson(person.getId()));
    assertEquals("person 1 doesn't exist", exception.getMessage());
  }

  @Test
  public void updatePerson_shouldReturnOk () {
    // GIVEN
    Person person = new Person();
    person.setId(1L);
    person.setFirstName("Homer");
    when(personRepository.existsById(anyLong())).thenReturn(TRUE);
    when(personRepository.save(any(Person.class))).thenReturn(person);

    // WHEN
    Person updated = personService.updatePerson(person.getId(), person);

    // THEN
    assertEquals(person.getFirstName(), updated.getFirstName());
    verify(personRepository).existsById(person.getId());
    verify(personRepository).save(person);
  }

  @Test
  public void updatePerson_shouldReturnNotFound () {
    // GIVEN
    Person person = new Person();
    person.setId(1L);

    // WHEN
    when(personRepository.existsById(anyLong())).thenReturn(FALSE);

    // THEN
    Throwable exception = assertThrows(EntityNotFoundException.class, () -> personService.updatePerson(person.getId(), person));
    assertEquals("person 1 doesn't exist", exception.getMessage());
  }

  @Test
  public void getPerson_shouldReturnOk () {
    // GIVEN
    Person person = new Person();
    person.setId(1L);
    when(personRepository.findById(anyLong())).thenReturn(java.util.Optional.of(person));

    // WHEN
    Person expected = personService.getPerson(person.getId());

    // THEN
    assertEquals(expected, person);
    verify(personRepository).findById(person.getId());
  }

  @Test
  public void getPerson_shouldReturnNotFound () {
    // GIVEN
    Person person = new Person();
    person.setId(1L);

    // WHEN
    when(personRepository.findById(anyLong())).thenReturn(null);

    // THEN
    assertThrows(NullPointerException.class, () -> personService.getPerson(person.getId()));
  }

}
