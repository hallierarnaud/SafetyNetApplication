package com.openclassrooms.safetynet.serviceTest;

import com.openclassrooms.safetynet.controller.DTO.PersonAddOrUpdateRequest;
import com.openclassrooms.safetynet.domain.object.FireStation;
import com.openclassrooms.safetynet.domain.object.MedicalRecord;
import com.openclassrooms.safetynet.domain.object.Person;
import com.openclassrooms.safetynet.domain.service.MapService;
import com.openclassrooms.safetynet.domain.service.MedicalRecordService;
import com.openclassrooms.safetynet.domain.service.PersonService;
import com.openclassrooms.safetynet.model.DAO.FireStationDAO;
import com.openclassrooms.safetynet.model.DAO.PersonDAO;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import javax.persistence.EntityExistsException;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonServiceTest {

  @Mock
  private PersonDAO personDAO;

  @Mock
  private FireStationDAO fireStationDAO;

  @Mock
  private MedicalRecordService medicalRecordService;

  @Mock
  private MapService mapService;

  @InjectMocks
  private PersonService personService;

  @Test
  public void addPersonTest_shouldReturnOk () {
    // GIVEN
    PersonAddOrUpdateRequest personAddRequest = new PersonAddOrUpdateRequest();
    personAddRequest.setFirstName("Homer");
    Person person = new Person();
    when(personDAO.addSimplePerson(any(Person.class))).thenReturn(person);

    // WHEN
    Person created = personService.addSimplePerson(personAddRequest);

    // THEN
    assertEquals(person.getFirstName(), created.getFirstName());
    verify(personDAO).addSimplePerson(any(Person.class));
  }

  @Test
  public void addPersonTest_shouldReturnAlreadyExist () {
    // GIVEN
    PersonAddOrUpdateRequest personAddRequest = new PersonAddOrUpdateRequest();
    personAddRequest.setId(1L);

    // WHEN
    when(personDAO.existById(anyLong())).thenReturn(TRUE);

    // THEN
    Throwable exception = assertThrows(EntityExistsException.class, () -> personService.addSimplePerson(personAddRequest));
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
    Person person = new Person();
    person.setFirstName("John");
    person.setLastName("Boyd");
    when(personDAO.existByFirstAndLastName(anyString(), anyString())).thenReturn(TRUE);

    // WHEN
    personService.deletePerson(person.getFirstName(), person.getLastName());

    // THEN
    verify(personDAO).deleteByFirstAndLastName(person.getFirstName(), person.getLastName());
  }

  @Test
  public void deletePerson_shouldReturnNotFound () {
    // GIVEN
    Person person = new Person();
    person.setFirstName("John");
    person.setLastName("Boyd");

    // WHEN
    when(personDAO.existByFirstAndLastName(anyString(), anyString())).thenReturn(FALSE);

    // THEN
    Throwable exception = assertThrows(NoSuchElementException.class, () -> personService.deletePerson(person.getFirstName(), person.getLastName()));
    assertEquals("John Boyd doesn't exist", exception.getMessage());
  }

  @Test
  public void updatePerson_shouldReturnOk () {
    // GIVEN
    PersonAddOrUpdateRequest personUpdateRequest = new PersonAddOrUpdateRequest();
    personUpdateRequest.setCity("Springville");
    Person person = new Person();
    person.setId(1L);
    when(personDAO.findById(anyLong())).thenReturn(person);
    when(personDAO.updateSimplePerson(anyLong(), any(Person.class))).thenReturn(person);

    // WHEN
    Person updated = personService.updateSimplePerson(person.getId(), personUpdateRequest);

    // THEN
    assertEquals(person.getCity(), updated.getCity());
    verify(personDAO, times(2)).findById(person.getId());
    verify(personDAO).updateSimplePerson(person.getId(), person);
  }

  @Test
  public void updatePerson_shouldReturnNotFound () {
    // GIVEN
    Person person = new Person();
    person.setId(100L);
    PersonAddOrUpdateRequest personUpdateRequest = new PersonAddOrUpdateRequest();

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

  @Test
  public void getPersonsByFireStation_shouldReturnOk () {
    // GIVEN
    Person person = new Person();
    person.setId(1L);
    person.setFirstName("Homer");
    List<Person> personByFireStationList = new ArrayList<>();
    personByFireStationList.add(person);
    when(personDAO.getPersonsByFireStationNumber(anyString())).thenReturn(personByFireStationList);
    MedicalRecord medicalRecord = new MedicalRecord();
    medicalRecord.setBirthdate("01/01/2020");
    when(personDAO.getPersonMedicalRecord(anyLong())).thenReturn(medicalRecord);

    // WHEN
    String expectedFirstName = personService.getPersonsByFireStation(anyString()).getPersonsByFireStation().get(0).getFirstName();
    int expectedMinorNumber = personService.getPersonsByFireStation(anyString()).getMinorNumber();

    // THEN
    assertEquals("Homer", expectedFirstName);
    assertEquals(1, expectedMinorNumber);
    verify(personDAO, times(4)).getPersonsByFireStationNumber(anyString());
    verify(personDAO, times (2)).getPersonMedicalRecord(anyLong());
  }

  @Test
  public void getChildrenByAddress_shouldReturnOk () {
    // GIVEN
    Person person = new Person();
    person.setId(1L);
    person.setFirstName("Homer");
    person.setAddress("1st Springville road");
    List<Person> personList = new ArrayList<>();
    personList.add(person);
    when(personDAO.findAll()).thenReturn(personList);
    List<Person> childrenByAddressList = new ArrayList<>();
    childrenByAddressList.add(person);
    when(personDAO.getPersonByAddress(anyString())).thenReturn(childrenByAddressList);
    MedicalRecord medicalRecord = new MedicalRecord();
    medicalRecord.setBirthdate("01/01/2020");
    when(personDAO.getPersonMedicalRecord(anyLong())).thenReturn(medicalRecord);

    // WHEN
    String expectedFirstName = personService.getChildrenByAddress("1st Springville road").getChildrenByAddressList().get(0).getFirstName();

    // THEN
    assertEquals("Homer", expectedFirstName);
    verify(personDAO).findAll();
    verify(personDAO).getPersonByAddress(anyString());
    verify(personDAO).getPersonMedicalRecord(anyLong());
  }

  @Test
  public void getChildrenByAddress_shouldReturnAdult () {
    // GIVEN
    Person person = new Person();
    person.setId(1L);
    person.setFirstName("Homer");
    person.setAddress("1st Springville road");
    List<Person> personList = new ArrayList<>();
    personList.add(person);
    when(personDAO.findAll()).thenReturn(personList);
    List<Person> childrenByAddressList = new ArrayList<>();
    childrenByAddressList.add(person);
    when(personDAO.getPersonByAddress(anyString())).thenReturn(childrenByAddressList);
    MedicalRecord medicalRecord = new MedicalRecord();
    medicalRecord.setBirthdate("01/01/2000");
    when(personDAO.getPersonMedicalRecord(anyLong())).thenReturn(medicalRecord);

    // WHEN
    String expectedFirstName = personService.getChildrenByAddress("1st Springville road").getAdultByAddressList().get(0).getFirstName();

    // THEN
    assertEquals("Homer", expectedFirstName);
    verify(personDAO).findAll();
    verify(personDAO).getPersonByAddress(anyString());
    verify(personDAO).getPersonMedicalRecord(anyLong());
  }

  @Test
  public void getPhonesByFireStation_shouldReturnOk () {
    // GIVEN
    Person person = new Person();
    person.setId(1L);
    person.setPhone("555-555-5555");
    List<Person> phoneByFireStationList = new ArrayList<>();
    phoneByFireStationList.add(person);
    when(personDAO.getPersonsByFireStationNumber(anyString())).thenReturn(phoneByFireStationList);

    // WHEN
    String expectedPhone = personService.getPhonesByFireStation(anyString()).get(0).getPhone();

    // THEN
    assertEquals("555-555-5555", expectedPhone);
    verify(personDAO, times(2)).getPersonsByFireStationNumber(anyString());
  }

  @Test
  public void getPersonsByAddress_shouldReturnOk () {
    // GIVEN
    Person person = new Person();
    person.setId(1L);
    person.setLastName("Simpson");
    List<Person> personsByAddressList = new ArrayList<>();
    personsByAddressList.add(person);
    when(personDAO.getPersonByAddress(anyString())).thenReturn(personsByAddressList);
    MedicalRecord medicalRecord = new MedicalRecord();
    medicalRecord.setBirthdate("01/01/2000");
    List<String> medications = Arrays.asList("aspirin", "ivermectine");
    medicalRecord.setMedications(medications);
    when(personDAO.getPersonMedicalRecord(anyLong())).thenReturn(medicalRecord);
    FireStation fireStation = new FireStation();
    fireStation.setStationNumber("2");
    when(personDAO.getPersonFireStation(anyLong())).thenReturn(fireStation);

    // WHEN
    String expectedLastName = personService.getPersonsByAddress(anyString()).getNamePhoneAgeAndMedicalRecordResponseList().get(0).getLastName();
    int expectedAge = personService.getPersonsByAddress(anyString()).getNamePhoneAgeAndMedicalRecordResponseList().get(0).getAge();
    String expectedMedication = personService.getPersonsByAddress(anyString()).getNamePhoneAgeAndMedicalRecordResponseList().get(0).getMedications().get(0);

    // THEN
    assertEquals("Simpson", expectedLastName);
    assertEquals(21, expectedAge);
    assertEquals("aspirin", expectedMedication);
    verify(personDAO, times(6)).getPersonByAddress(anyString());
    verify(personDAO, times(9)).getPersonMedicalRecord(anyLong());
    verify(personDAO, times(3)).getPersonFireStation(anyLong());
  }

  @Test
  public void getFamiliesByFireStations_shouldReturnOk () {
    // GIVEN
    int stationNumber = 1;
    List<Integer> stationNumberList = new ArrayList<>();
    stationNumberList.add(stationNumber);
    Person person = new Person();
    person.setId(1L);
    person.setLastName("Simpson");
    person.setAddress("1st Springville road");
    FireStation fireStation = new FireStation();
    fireStation.setStationNumber("1");
    List<FireStation> fireStationList = new ArrayList<>();
    fireStationList.add(fireStation);
    when(fireStationDAO.findAll()).thenReturn(fireStationList);
    List<Person> personsByFireStationNumber = new ArrayList<>();
    personsByFireStationNumber.add(person);
    when(personDAO.getPersonsByFireStationNumber(anyString())).thenReturn(personsByFireStationNumber);
    List<Person> familyByFireStation = new ArrayList<>();
    familyByFireStation.add(person);
    when(personDAO.getPersonByAddress(anyString())).thenReturn(familyByFireStation);
    MedicalRecord medicalRecord = new MedicalRecord();
    medicalRecord.setBirthdate("01/01/2000");
    List<String> medications = Arrays.asList("aspirin", "ivermectine");
    medicalRecord.setMedications(medications);
    when(personDAO.getPersonMedicalRecord(anyLong())).thenReturn(medicalRecord);

    // WHEN
    String expectedLastName = personService.getFamiliesByFireStations(stationNumberList).get(0).get(0).get(0).getLastName();
    int expectedAge = personService.getFamiliesByFireStations(stationNumberList).get(0).get(0).get(0).getAge();
    String expectedMedication = personService.getFamiliesByFireStations(stationNumberList).get(0).get(0).get(0).getMedications().get(0);

    // THEN
    assertEquals("Simpson", expectedLastName);
    assertEquals(21, expectedAge);
    assertEquals("aspirin", expectedMedication);
    verify(personDAO, times(3)).getPersonsByFireStationNumber(anyString());
    verify(personDAO, times(3)).getPersonByAddress(anyString());
    verify(personDAO, times(9)).getPersonMedicalRecord(anyLong());
  }

  @Test
  public void getPersonInfo_shouldReturnOk () {
    // GIVEN
    Person person = new Person();
    person.setId(1L);
    person.setLastName("Simpson");
    List<Person> persons = new ArrayList<>();
    persons.add(person);
    when(personDAO.getPersonByLastName(anyString())).thenReturn(persons);
    MedicalRecord medicalRecord = new MedicalRecord();
    medicalRecord.setBirthdate("01/01/2000");
    List<String> medications = Arrays.asList("aspirin", "ivermectine");
    medicalRecord.setMedications(medications);
    when(personDAO.getPersonMedicalRecord(anyLong())).thenReturn(medicalRecord);

    // WHEN
    String expectedLastName = personService.getPersonInfo(anyString()).get(0).getLastName();
    int expectedAge = personService.getPersonInfo(anyString()).get(0).getAge();

    // THEN
    assertEquals("Simpson", expectedLastName);
    assertEquals(21, expectedAge);
    verify(personDAO, times(4)).getPersonByLastName(anyString());
    verify(personDAO, times(6)).getPersonMedicalRecord(anyLong());
  }

  @Test
  public void getEmailsByCity_shouldReturnOk () {
    // GIVEN
    Person person = new Person();
    person.setId(1L);
    person.setEmail("simpson@email.fr");
    List<Person> emailByCityList = new ArrayList<>();
    emailByCityList.add(person);
    when(personDAO.getPersonByCity(anyString())).thenReturn(emailByCityList);

    // WHEN
    String expectedEmail = personService.getEmailsByCity(anyString()).get(0).getEmail();

    // THEN
    assertEquals("simpson@email.fr", expectedEmail);
    verify(personDAO, times(2)).getPersonByCity(anyString());
  }

}
