package com.openclassrooms.safetynet.model.DAO;

import com.openclassrooms.safetynet.domain.object.FireStation;
import com.openclassrooms.safetynet.domain.object.MedicalRecord;
import com.openclassrooms.safetynet.domain.object.Person;
import com.openclassrooms.safetynet.model.entity.FireStationEntity;
import com.openclassrooms.safetynet.model.entity.MedicalRecordEntity;
import com.openclassrooms.safetynet.model.entity.PersonEntity;
import com.openclassrooms.safetynet.model.repository.FireStationRepository;
import com.openclassrooms.safetynet.model.repository.MedicalRecordRepository;
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

  @Autowired
  private MedicalRecordRepository medicalRecordRepository;

  @Autowired
  private FireStationRepository fireStationRepository;

  @Autowired
  MapDAO mapDAO;

  public Person findById(Long id) {
    PersonEntity personEntity = personRepository.findById(id).orElseThrow(() -> new NoSuchElementException("person " + id + " doesn't exist"));
    Person person = new Person();
    mapDAO.updatePersonWithPersonEntity(person, personEntity);
    return person;
  }

  public List<Person> findAll() {
    List<PersonEntity> personEntities =  StreamSupport.stream(personRepository.findAll().spliterator(),false)
            .collect(Collectors.toList());
    return personEntities.stream().map((personEntity) -> {
      Person person = new Person();
      mapDAO.updatePersonWithPersonEntity(person, personEntity);
      return person;
    }).collect(Collectors.toList());
  }

  public Person updateSimplePerson(Long id, Person person) {
    PersonEntity personEntity = personRepository.findById(id).orElseThrow(() -> new NoSuchElementException("person " + id + " doesn't exist"));
    mapDAO.updatePersonEntityWithPerson(personEntity, person);
    personRepository.save(personEntity);
    return person;
  }

  public Person addSimplePerson(Person person) {
    PersonEntity personEntity = new PersonEntity();
    personEntity.setFirstName(person.getFirstName());
    personEntity.setLastName(person.getLastName());
    mapDAO.updatePersonEntityWithPerson(personEntity, person);
    personRepository.save(personEntity);
    return person;
  }

  public Boolean existById(Long id) {
    return personRepository.existsById(id);
  }

  public Boolean existByFirstAndLastName(String firstName, String lastName) {
    return personRepository.existsByFirstNameAndLastName(firstName, lastName);
  }

  public void deleteByFirstAndLastName(String firstName, String lastName) {
    personRepository.deleteByFirstNameAndLastName(firstName, lastName);
  }

  public List<Person> getPersonsByFireStationNumber(String stationNumber) {
    List<PersonEntity> personEntities = personRepository.findAllByFireStationEntityStationNumber(stationNumber);
    return personEntities.stream().map((personEntity) -> {
      Person person = new Person();
      person.setId(personEntity.getId());
      person.setFirstName(personEntity.getFirstName());
      person.setLastName(personEntity.getLastName());
      person.setAddress(personEntity.getAddress());
      person.setPhone(personEntity.getPhone());
      return person;
    }).collect(Collectors.toList());
  }

  public List<Person> getPersonByAddress(String address) {
    List<PersonEntity> personEntities = personRepository.findAllByAddressLike(address);
    return personEntities.stream().map((personEntity) -> {
      Person person = new Person();
      person.setId(personEntity.getId());
      person.setFirstName(personEntity.getFirstName());
      person.setLastName(personEntity.getLastName());
      person.setPhone(personEntity.getPhone());
      return person;
    }).collect(Collectors.toList());
  }

  public FireStation getPersonFireStation(Long id) {
    PersonEntity person = personRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException());
    String personAddress = person.getAddress();
    FireStationEntity fireStationEntity = fireStationRepository.findAll()
            .stream()
            .filter(fireStation -> fireStation.getAddresses().contains(personAddress))
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException());
    FireStation fireStation = new FireStation();
    return mapDAO.updateFireStationWithFireStationEntity(fireStation, fireStationEntity);
  }

  public MedicalRecord getPersonMedicalRecord(Long id) {
    PersonEntity person = personRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException());
    MedicalRecordEntity medicalRecordEntity = medicalRecordRepository.findByPersonEntityFirstNameAndPersonEntityLastName(person.getFirstName(), person.getLastName());
    MedicalRecord medicalRecord = new MedicalRecord();
    return mapDAO.updateMedicalRecordWithMedicalRecordEntity(medicalRecord, medicalRecordEntity);
  }

  public List<Person> getPersonByLastName (String lastName) {
    List<PersonEntity> personEntities = personRepository.findByLastName(lastName);
    return personEntities.stream().map((personEntity) -> {
      Person person = new Person();
      person.setId(personEntity.getId());
      person.setLastName(personEntity.getLastName());
      person.setAddress(personEntity.getAddress());
      person.setEmail(personEntity.getEmail());
      return person;
    }).collect(Collectors.toList());
  }

}
