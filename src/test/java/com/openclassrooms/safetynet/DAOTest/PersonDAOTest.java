package com.openclassrooms.safetynet.DAOTest;

import com.openclassrooms.safetynet.domain.object.FireStation;
import com.openclassrooms.safetynet.domain.object.MedicalRecord;
import com.openclassrooms.safetynet.domain.object.Person;
import com.openclassrooms.safetynet.model.DAO.MapDAO;
import com.openclassrooms.safetynet.model.DAO.PersonDAO;
import com.openclassrooms.safetynet.model.entity.FireStationEntity;
import com.openclassrooms.safetynet.model.entity.MedicalRecordEntity;
import com.openclassrooms.safetynet.model.entity.PersonEntity;
import com.openclassrooms.safetynet.model.repository.FireStationRepository;
import com.openclassrooms.safetynet.model.repository.MedicalRecordRepository;
import com.openclassrooms.safetynet.model.repository.PersonRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonDAOTest {

  @Mock
  private PersonRepository personRepository;

  @Mock
  private FireStationRepository fireStationRepository;

  @Mock
  private MedicalRecordRepository medicalRecordRepository;

  @Mock
  private MapDAO mapDAO;

  @InjectMocks
  private PersonDAO personDAO;

  @Test
  public void findById_shouldReturnOk() {
    // GIVEN
    PersonEntity personEntity = new PersonEntity();
    Person person = new Person();
    when(personRepository.findById(anyLong())).thenReturn(java.util.Optional.of(personEntity));

    // WHEN
    Person personExpected = personDAO.findById(anyLong());

    // THEN
    assertEquals(personExpected, person);
    verify(personRepository).findById(anyLong());
  }

  @Test
  public void findById_shouldReturnNotFound() {
    // GIVEN
    Person person = new Person();
    person.setId(100L);

    // THEN
    assertThrows(NoSuchElementException.class, () -> personDAO.findById(person.getId()));
  }

  @Test
  public void findAll_shouldReturnOk() {
    // GIVEN
    List<PersonEntity> personEntities = new ArrayList<>();
    personEntities.add(new PersonEntity());
    List<Person> persons = new ArrayList<>();
    persons.add(new Person());
    when(personRepository.findAll()).thenReturn(personEntities);

    // WHEN
    List<Person> expected = personDAO.findAll();

    // THEN
    assertEquals(expected, persons);
    verify(personRepository).findAll();
  }

  @Test
  public void updateSimplePerson_shouldReturnOK() {
    // GIVEN
    PersonEntity personEntity = new PersonEntity();
    Person person = new Person();
    person.setId(1L);
    person.setFirstName("Homer");
    when(personRepository.findById(anyLong())).thenReturn(java.util.Optional.of(personEntity));
    when(personRepository.save(any(PersonEntity.class))).thenReturn(personEntity);

    // WHEN
    String expectedFirstName = personDAO.updateSimplePerson(1l, person).getFirstName();

    // THEN
    assertEquals("Homer", expectedFirstName);
    verify(personRepository).findById(anyLong());
    verify(personRepository).save(any(PersonEntity.class));
  }

  @Test
  public void updateSimplePerson_shouldReturnNotFound() {
    // GIVEN
    Person person = new Person();
    person.setId(100L);

    // THEN
    Throwable exception = assertThrows(NoSuchElementException.class, () -> personDAO.updateSimplePerson(person.getId(), person));
    assertEquals("person 100 doesn't exist", exception.getMessage());
  }

  @Test
  public void addSimplePerson_shouldReturnOK() {
    // GIVEN
    PersonEntity personEntity = new PersonEntity();
    Person person = new Person();
    person.setId(1L);
    person.setFirstName("Homer");
    when(personRepository.save(any(PersonEntity.class))).thenReturn(personEntity);

    // WHEN
    String expectedFirstName = personDAO.addSimplePerson(person).getFirstName();

    // THEN
    assertEquals("Homer", expectedFirstName);
    verify(personRepository).save(any(PersonEntity.class));
  }

  @Test
  public void existById_shouldReturnOk() {
    // GIVEN
    when(personRepository.existsById(anyLong())).thenReturn(true);

    // WHEN
    Boolean existExpected = personDAO.existById(anyLong());

    // THEN
    assertEquals(true, existExpected);
    verify(personRepository).existsById(anyLong());
  }

  @Test
  public void existByFirstNameAndLastName_shouldReturnOk() {
    // GIVEN
    when(personRepository.existsByFirstNameAndLastName(anyString(), anyString())).thenReturn(true);

    // WHEN
    Boolean existExpected = personDAO.existByFirstAndLastName(anyString(), anyString());

    // THEN
    assertEquals(true, existExpected);
    verify(personRepository).existsByFirstNameAndLastName(anyString(), anyString());
  }

  @Test
  public void deleteByFirstNameAndLastName_shouldReturnOk() {
    // GIVEN
    PersonEntity personEntity = new PersonEntity();
    personEntity.setFirstName("Homer");
    personEntity.setLastName("Simpson");

    // WHEN
    personRepository.deleteByFirstNameAndLastName(personEntity.getFirstName(), personEntity.getLastName());

    // THEN
    verify(personRepository).deleteByFirstNameAndLastName(anyString(), anyString());
  }

  @Test
  public void getPersonsByFireStationNumber_shouldReturnOk() {
    // GIVEN
    PersonEntity personEntity = new PersonEntity();
    personEntity.setId(1L);
    personEntity.setFirstName("Homer");
    List<PersonEntity> personEntityByFireStationList = new ArrayList<>();
    personEntityByFireStationList.add(personEntity);
    when(personRepository.findAllByFireStationEntityStationNumber(anyString())).thenReturn(personEntityByFireStationList);

    // WHEN
    String expectedFirstName = personDAO.getPersonsByFireStationNumber(anyString()).get(0).getFirstName();

    // THEN
    assertEquals("Homer", expectedFirstName);
    verify(personRepository).findAllByFireStationEntityStationNumber(anyString());
  }

  @Test
  public void getPersonsByAddress_shouldReturnOk() {
    // GIVEN
    PersonEntity personEntity = new PersonEntity();
    personEntity.setId(1L);
    personEntity.setFirstName("Homer");
    List<PersonEntity> personEntities = new ArrayList<>();
    personEntities.add(personEntity);
    when(personRepository.findAllByAddressLike(anyString())).thenReturn(personEntities);

    // WHEN
    String expectedFirstName = personDAO.getPersonByAddress(anyString()).get(0).getFirstName();

    // THEN
    assertEquals("Homer", expectedFirstName);
    verify(personRepository).findAllByAddressLike(anyString());
  }

  @Test
  public void getPersonFireStation_shouldReturnOk() {
    // GIVEN
    PersonEntity personEntity = new PersonEntity();
    personEntity.setAddress("SpringRoad");
    when(personRepository.findById(anyLong())).thenReturn(java.util.Optional.of(personEntity));
    FireStationEntity fireStationEntity = new FireStationEntity();
    fireStationEntity.setStationNumber("1");
    Set<String> addresses = new HashSet<>();
    addresses.add("SpringRoad");
    fireStationEntity.setAddresses(addresses);
    List<FireStationEntity> fireStationEntities = new ArrayList<>();
    fireStationEntities.add(fireStationEntity);
    when(fireStationRepository.findAll()).thenReturn(fireStationEntities);
    FireStation fireStation = new FireStation();
    when(mapDAO.updateFireStationWithFireStationEntity(fireStation, fireStationEntity)).thenReturn(fireStation);

    // WHEN
    FireStation expectedFireStation = personDAO.getPersonFireStation(anyLong());

    // THEN
    assertEquals(fireStation, expectedFireStation);
    verify(personRepository).findById(anyLong());
    verify(fireStationRepository).findAll();
    verify(mapDAO).updateFireStationWithFireStationEntity(any(FireStation.class), any(FireStationEntity.class));
  }

  @Test
  public void getPersonFireStation_shouldReturnNotFound() {
    // GIVEN
    PersonEntity personEntity = new PersonEntity();
    personEntity.setAddress("SpringRoad");
    when(personRepository.findById(anyLong())).thenReturn(java.util.Optional.of(personEntity));
    FireStationEntity fireStationEntity = new FireStationEntity();
    fireStationEntity.setStationNumber("1");
    Set<String> addresses = new HashSet<>();
    addresses.add("Spring");
    fireStationEntity.setAddresses(addresses);
    List<FireStationEntity> fireStationEntities = new ArrayList<>();
    fireStationEntities.add(fireStationEntity);
    when(fireStationRepository.findAll()).thenReturn(fireStationEntities);

    // THEN
    assertThrows(NoSuchElementException.class, () -> personDAO.getPersonFireStation(anyLong()));
    verify(personRepository).findById(anyLong());
    verify(fireStationRepository).findAll();
  }

  @Test
  public void getPersonMedicalRecord_shouldReturnOk() {
    // GIVEN
    PersonEntity personEntity = new PersonEntity();
    personEntity.setFirstName("Homer");
    personEntity.setLastName("Simpson");
    when(personRepository.findById(anyLong())).thenReturn(java.util.Optional.of(personEntity));
    MedicalRecordEntity medicalRecordEntity = new MedicalRecordEntity();
    when(medicalRecordRepository.findByPersonEntityFirstNameAndPersonEntityLastName(personEntity.getFirstName(), personEntity.getLastName())).thenReturn(medicalRecordEntity);
    MedicalRecord medicalRecord = new MedicalRecord();
    when(mapDAO.updateMedicalRecordWithMedicalRecordEntity(medicalRecord, medicalRecordEntity)).thenReturn(medicalRecord);

    // WHEN
    MedicalRecord expectedMedicalRecord = personDAO.getPersonMedicalRecord(anyLong());

    // THEN
    assertEquals(medicalRecord, expectedMedicalRecord);
    verify(personRepository).findById(anyLong());
    verify(medicalRecordRepository).findByPersonEntityFirstNameAndPersonEntityLastName(anyString(), anyString());
    verify(mapDAO).updateMedicalRecordWithMedicalRecordEntity(any(MedicalRecord.class),any(MedicalRecordEntity.class));
  }

  @Test
  public void getPersonMedicalRecord_shouldNotFound() {
    // GIVEN
    Person person = new Person();
    person.setId(100L);

    // THEN
    assertThrows(NoSuchElementException.class, () -> personDAO.getPersonMedicalRecord(person.getId()));
  }

  @Test
  public void getPersonByLastName_shouldReturnOk() {
    // GIVEN
    PersonEntity personEntity = new PersonEntity();
    personEntity.setId(1L);
    personEntity.setLastName("Simpson");
    List<PersonEntity> personEntities = new ArrayList<>();
    personEntities.add(personEntity);
    when(personRepository.findByLastName(anyString())).thenReturn(personEntities);

    // WHEN
    String expectedLastName = personDAO.getPersonByLastName(anyString()).get(0).getLastName();

    // THEN
    assertEquals("Simpson", expectedLastName);
    verify(personRepository).findByLastName(anyString());
  }

  @Test
  public void getPersonByCity_shouldReturnOk() {
    //GIVEN
    PersonEntity personEntity = new PersonEntity();
    personEntity.setId(1L);
    personEntity.setEmail("homerSimpson@email.com");
    List<PersonEntity> personEntities = new ArrayList<>();
    personEntities.add(personEntity);
    when(personRepository.findAllByCity(anyString())).thenReturn(personEntities);

    // WHEN
    String expectedEmail = personDAO.getPersonByCity(anyString()).get(0).getEmail();

    // THEN
    assertEquals("homerSimpson@email.com", expectedEmail);
    verify(personRepository).findAllByCity(anyString());
  }

}
