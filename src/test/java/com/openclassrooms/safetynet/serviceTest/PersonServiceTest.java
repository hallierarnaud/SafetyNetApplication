package com.openclassrooms.safetynet.serviceTest;

import com.openclassrooms.safetynet.controller.DTO.PersonMedicalRecordResponse;
import com.openclassrooms.safetynet.controller.DTO.PersonUpdateRequest;
import com.openclassrooms.safetynet.domain.object.Person;
import com.openclassrooms.safetynet.domain.service.MapService;
import com.openclassrooms.safetynet.domain.service.PersonService;
import com.openclassrooms.safetynet.model.DAO.PersonDAO;
import com.openclassrooms.safetynet.model.entity.PersonEntity;
import com.openclassrooms.safetynet.model.repository.PersonRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.persistence.EntityExistsException;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

  @Mock
  private PersonDAO personDAO;

  @Mock
  private PersonRepository personRepository;

  @Mock
  private MapService mapService;

  @InjectMocks
  private PersonService personService;

  @Test
  public void addPersonTest_shouldReturnOk () {
    // GIVEN
    PersonEntity person = new PersonEntity();
    person.setFirstName("Homer");
    PersonMedicalRecordResponse personMedicalRecordResponse = new PersonMedicalRecordResponse();
    when(personRepository.save(any(PersonEntity.class))).thenReturn(person);

    // WHEN
    PersonEntity created = personService.addPerson(personMedicalRecordResponse);

    // THEN
    assertEquals(person.getFirstName(), created.getFirstName());
    verify(personRepository).save(any(PersonEntity.class));
  }

  @Test
  public void addPersonTest_shouldReturnAlreadyExist () {
    // GIVEN
    PersonEntity person = new PersonEntity();
    person.setId(1L);
    PersonMedicalRecordResponse personMedicalRecordResponse = new PersonMedicalRecordResponse();
    personMedicalRecordResponse.setId(person.getId());

    // WHEN
    when(personRepository.existsById(anyLong())).thenReturn(TRUE);

    // THEN
    Throwable exception = assertThrows(EntityExistsException.class, () -> personService.addPerson(personMedicalRecordResponse));
    assertEquals("person 1 already exists", exception.getMessage());
  }

  @Test
  public void getPersons_shouldReturnOk () {
    // GIVEN
    List<Person> persons = new ArrayList();
    persons.add(new Person());
    when(personDAO.findAll()).thenReturn(persons);

    // WHEN
    List<Person> expected = personService.getPersons();

    // THEN
    assertEquals(expected, persons);
    verify(personDAO).findAll();
  }

  @Test
  public void deletePerson_shouldReturnOk () {
    // GIVEN
    PersonEntity person = new PersonEntity();
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
    PersonEntity person = new PersonEntity();
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
    PersonUpdateRequest personUpdateRequest = new PersonUpdateRequest();
    personUpdateRequest.setFirstName("Homer");
    Person person = new Person();
    person.setId(1L);
    when(personDAO.findById(anyLong())).thenReturn(person);
    when(personDAO.updateSimplePerson(anyLong(), any(Person.class))).thenReturn(person);

    // WHEN
    Person updated = personService.updateSimplePerson(person.getId(), personUpdateRequest);

    // THEN
    assertEquals(person.getFirstName(), updated.getFirstName());
    verify(personDAO, times(2)).findById(person.getId());
    verify(personDAO).updateSimplePerson(person.getId(), person);
  }

  @Test
  public void updatePerson_shouldReturnNotFound () {
    // GIVEN
    Person person = new Person();
    person.setId(100L);
    PersonUpdateRequest personUpdateRequest = new PersonUpdateRequest();

    // THEN
    Throwable exception = assertThrows(NoSuchElementException.class, () -> personService.updateSimplePerson(person.getId(), personUpdateRequest));
    assertEquals("person 100 doesn't exist", exception.getMessage());
  }

  @Test
  public void getPerson_shouldReturnOk () {
    // GIVEN
    Person person = new Person();
    person.setId(1L);
    when(personDAO.findById(anyLong())).thenReturn(person);

    // WHEN
    Person expected = personService.getPerson(person.getId());

    // THEN
    assertEquals(expected, person);
    verify(personDAO, times(2)).findById(person.getId());
  }

  @Test
  public void getPerson_shouldReturnNotFound () {
    // GIVEN
    Person person = new Person();
    person.setId(100L);

    // WHEN
    when(personDAO.findById(anyLong())).thenReturn(null);

    // THEN
    assertThrows(NoSuchElementException.class, () -> personService.getPerson(person.getId()));
  }

}
