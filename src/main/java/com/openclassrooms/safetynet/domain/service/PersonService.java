package com.openclassrooms.safetynet.domain.service;

import com.openclassrooms.safetynet.controller.DTO.PersonAddOrUpdateRequest;
import com.openclassrooms.safetynet.domain.object.Person;
import com.openclassrooms.safetynet.model.DAO.PersonDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.persistence.EntityExistsException;

import lombok.Data;

@Data
@Service
public class PersonService {

  // TODO : Repository pourront être supprimés une fois que les DAO feront le job
  /*@Autowired
  private PersonRepository personRepository;

  @Autowired
  private FireStationRepository fireStationRepository;*/

  @Autowired
  private PersonDAO personDAO;

  @Autowired
  private MedicalRecordService medicalRecordService;

  @Autowired
  private MapService mapService;

  public Person getPerson(final Long id) {
    if (personDAO.findById(id) == null) {
      throw new NoSuchElementException("person " + id + " doesn't exist");
    }
    return personDAO.findById(id);
  }

  public List<Person> getPersons() {
    return StreamSupport.stream(personDAO.findAll().spliterator(),false)
            .collect(Collectors.toList());
  }

  public void deletePerson(final String firstName, final String lastName) {
    if (!personDAO.existByFirstAndLastName(firstName, lastName)) {
      throw new NoSuchElementException(firstName + " " + lastName + " doesn't exist");
    }
    personDAO.deleteByFirstAndLastName(firstName, lastName);
  }

  public Person updateSimplePerson(final Long id, PersonAddOrUpdateRequest personUpdateRequest) {
    if (personDAO.findById(id) == null) {
      throw new NoSuchElementException("person " + id + " doesn't exist");
    }
    Person person = personDAO.findById(id);
    person.setId(id);
    mapService.updatePersonWithPersonRequest(person, personUpdateRequest);
    return personDAO.updateSimplePerson(id, person);
  }

  /*public PersonEntity addPerson(PersonMedicalRecordResponse personMedicalRecordResponse) {
    PersonEntity person = new PersonEntity();
    MedicalRecordEntity medicalRecord = new MedicalRecordEntity();
    person.setMedicalRecord(medicalRecord);
    medicalRecord.setPerson(person);
    mapService.updatePersonWithPersonMedicalDTO(person, medicalRecord, personMedicalRecordResponse);
    if (personRepository.existsById(personMedicalRecordResponse.getId())) {
      throw new EntityExistsException("person " + personMedicalRecordResponse.getId() + " already exists");
    }
    return personRepository.save(person);
  }*/

  public Person addSimplePerson(PersonAddOrUpdateRequest personAddRequest) {
    if (personDAO.existById(personAddRequest.getId())) {
      throw new EntityExistsException("person " + personAddRequest.getId() + " already exists");
    }
    Person person = new Person();
    person.setId(personAddRequest.getId());
    person.setFirstName(personAddRequest.getFirstName());
    person.setLastName(personAddRequest.getLastName());
    mapService.updatePersonWithPersonRequest(person, personAddRequest);
    return personDAO.addSimplePerson(person);
  }

  public List<Person> getPersonsByFireStationNumber(String stationNumber) {
    return personDAO.getPersonsByFireStationNumber(stationNumber);
  }

  public int countMinorByFireStation(String stationNumber) {
    int minorNumber= 0;
    List<Person> personByFireStationList = personDAO.getPersonsByFireStationNumber(stationNumber);
    for (Person person : personByFireStationList) {
      LocalDate currentDate = LocalDate.now();
      DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/d/yyyy");
      String stringPersonBirthDate = medicalRecordService.getMedicalRecord(person.getId()).getBirthdate();
      LocalDate datePersonBirthDate = LocalDate.parse(stringPersonBirthDate, dateTimeFormatter);
      Period personAge = Period.between(datePersonBirthDate, currentDate);
      if (personAge.getYears() < 18.0) {
        minorNumber++;
      }
    }
    return minorNumber;
  }

  /*public List<Person> findByLastNameLike(String lastName) {
    return personDAO.findByLastName(lastName);
  }*/

  /*public FireStationEntity getPersonFireStation(Long id) {
    PersonEntity person = personRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException());
    String personAddress = person.getAddress();
    return fireStationRepository.findAll()
            .stream()
            .filter(fireStation -> fireStation.getAddresses().contains(personAddress))
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException());
  }*/

}
