package com.openclassrooms.safetynet.domain.service;

import com.openclassrooms.safetynet.controller.DTO.ChildrenByAddressResponse;
import com.openclassrooms.safetynet.controller.DTO.FirstLastNameAndAgeResponse;
import com.openclassrooms.safetynet.controller.DTO.NamePhoneAgeAndMedicalRecordResponse;
import com.openclassrooms.safetynet.controller.DTO.PersonAddOrUpdateRequest;
import com.openclassrooms.safetynet.controller.DTO.PersonByAddressResponse;
import com.openclassrooms.safetynet.controller.DTO.PersonByFireStationResponse;
import com.openclassrooms.safetynet.controller.DTO.PhoneResponse;
import com.openclassrooms.safetynet.controller.DTO.ShortPersonResponse;
import com.openclassrooms.safetynet.domain.object.Person;
import com.openclassrooms.safetynet.model.DAO.PersonDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.persistence.EntityExistsException;

import lombok.Data;

@Data
@Service
public class PersonService {

  @Autowired
  private PersonDAO personDAO;

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

  public PersonByFireStationResponse getPersonsByFireStation(String stationNumber) {
    int minorNumber = 0;
    List<Person> personByFireStationList = personDAO.getPersonsByFireStationNumber(stationNumber);
    List<ShortPersonResponse> shortPersonResponseList = new ArrayList<>();
    for (Person personByFireStation : personByFireStationList) {
      ShortPersonResponse shortPersonResponse = new ShortPersonResponse();
      shortPersonResponse.setFirstName(personByFireStation.getFirstName());
      shortPersonResponse.setLastName(personByFireStation.getLastName());
      shortPersonResponse.setAddress(personByFireStation.getAddress());
      shortPersonResponse.setPhone(personByFireStation.getPhone());
      shortPersonResponseList.add(shortPersonResponse);
      LocalDate currentDate = LocalDate.now();
      DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/d/yyyy");
      String stringPersonBirthDate = personDAO.getPersonMedicalRecord(personByFireStation.getId()).getBirthdate();
      LocalDate datePersonBirthDate = LocalDate.parse(stringPersonBirthDate, dateTimeFormatter);
      Period personAge = Period.between(datePersonBirthDate, currentDate);
      if (personAge.getYears() < 18.0) {
        minorNumber++;
      }
    }
    PersonByFireStationResponse personByFireStationResponse = new PersonByFireStationResponse();
    personByFireStationResponse.setPersonsByFireStation(shortPersonResponseList);
    personByFireStationResponse.setMinorNumber(minorNumber);
    personByFireStationResponse.setMajorNumber(personByFireStationList.size() - minorNumber);
    return personByFireStationResponse;
  }

  public ChildrenByAddressResponse getChildrenByAddress(String address) {
    List<Person> childrenByAddressList = personDAO.getPersonByAddress(address);
    List<FirstLastNameAndAgeResponse> childrenFirstLastNameAndAgeResponseList = new ArrayList<>();
    List<FirstLastNameAndAgeResponse> adultFirstLastNameAndAgeResponseList = new ArrayList<>();
    for (Person childrenByAddress : childrenByAddressList) {
      LocalDate currentDate = LocalDate.now();
      DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/d/yyyy");
      String stringPersonBirthDate = personDAO.getPersonMedicalRecord(childrenByAddress.getId()).getBirthdate();
      LocalDate datePersonBirthDate = LocalDate.parse(stringPersonBirthDate, dateTimeFormatter);
      Period personAge = Period.between(datePersonBirthDate, currentDate);
      if (personAge.getYears() < 18.0) {
        FirstLastNameAndAgeResponse firstLastNameAndAgeResponse = new FirstLastNameAndAgeResponse();
        firstLastNameAndAgeResponse.setFirstName(childrenByAddress.getFirstName());
        firstLastNameAndAgeResponse.setLastName(childrenByAddress.getLastName());
        firstLastNameAndAgeResponse.setAge(personAge.getYears());
        childrenFirstLastNameAndAgeResponseList.add(firstLastNameAndAgeResponse);
      } else {
        FirstLastNameAndAgeResponse firstLastNameAndAgeResponse = new FirstLastNameAndAgeResponse();
        firstLastNameAndAgeResponse.setFirstName(childrenByAddress.getFirstName());
        firstLastNameAndAgeResponse.setLastName(childrenByAddress.getLastName());
        firstLastNameAndAgeResponse.setAge(personAge.getYears());
        adultFirstLastNameAndAgeResponseList.add(firstLastNameAndAgeResponse);
      }
    }
    ChildrenByAddressResponse childrenByAddressResponse = new ChildrenByAddressResponse();
    childrenByAddressResponse.setChildrenByAddressList(childrenFirstLastNameAndAgeResponseList);
    childrenByAddressResponse.setAdultByAddressList(adultFirstLastNameAndAgeResponseList);
    return childrenByAddressResponse;
  }

  public List<PhoneResponse> getPhonesByFireStation(String stationNumber) {
    List<Person> personByFireStationList = personDAO.getPersonsByFireStationNumber(stationNumber);
    List<PhoneResponse> phoneByFireStationList = new ArrayList<>();
    for (Person personByFireStation : personByFireStationList) {
      PhoneResponse phoneResponse = new PhoneResponse();
      phoneResponse.setPhone(personByFireStation.getPhone());
      phoneByFireStationList.add(phoneResponse);
    }
    return phoneByFireStationList;
  }

  public PersonByAddressResponse getPersonsByAddress(String address) {
    List<Person> personByAddressList = personDAO.getPersonByAddress(address);
    List<NamePhoneAgeAndMedicalRecordResponse> namePhoneAgeAndMedicalRecordList = new ArrayList<>();
    for (Person personByAddress : personByAddressList) {
      LocalDate currentDate = LocalDate.now();
      DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/d/yyyy");
      String stringPersonBirthDate = personDAO.getPersonMedicalRecord(personByAddress.getId()).getBirthdate();
      LocalDate datePersonBirthDate = LocalDate.parse(stringPersonBirthDate, dateTimeFormatter);
      Period personAge = Period.between(datePersonBirthDate, currentDate);
      NamePhoneAgeAndMedicalRecordResponse namePhoneAgeAndMedicalRecordResponse = new NamePhoneAgeAndMedicalRecordResponse();
      namePhoneAgeAndMedicalRecordResponse.setLastName(personByAddress.getLastName());
      namePhoneAgeAndMedicalRecordResponse.setPhone(personByAddress.getPhone());
      namePhoneAgeAndMedicalRecordResponse.setAge(personAge.getYears());
      namePhoneAgeAndMedicalRecordResponse.setMedications(personDAO.getPersonMedicalRecord(personByAddress.getId()).getMedications());
      namePhoneAgeAndMedicalRecordResponse.setAllergies(personDAO.getPersonMedicalRecord(personByAddress.getId()).getAllergies());
      namePhoneAgeAndMedicalRecordList.add(namePhoneAgeAndMedicalRecordResponse);
    }
    PersonByAddressResponse personByAddressResponse = new PersonByAddressResponse();
    personByAddressResponse.setNamePhoneAgeAndMedicalRecordResponseList(namePhoneAgeAndMedicalRecordList);
    personByAddressResponse.setStationNumber(personDAO.getPersonFireStation(personByAddressList.get(0).getId()).getStationNumber());
    return personByAddressResponse;
  }

}
