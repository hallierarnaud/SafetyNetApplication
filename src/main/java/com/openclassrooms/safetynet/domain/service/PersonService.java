package com.openclassrooms.safetynet.domain.service;

import com.openclassrooms.safetynet.controller.DTO.ChildrenByAddressResponse;
import com.openclassrooms.safetynet.controller.DTO.EmailResponse;
import com.openclassrooms.safetynet.controller.DTO.FirstLastNameAndAgeResponse;
import com.openclassrooms.safetynet.controller.DTO.NamePhoneAgeAndMedicalRecordResponse;
import com.openclassrooms.safetynet.controller.DTO.PersonAddOrUpdateRequest;
import com.openclassrooms.safetynet.controller.DTO.PersonByAddressResponse;
import com.openclassrooms.safetynet.controller.DTO.PersonByFireStationResponse;
import com.openclassrooms.safetynet.controller.DTO.PersonInfoResponse;
import com.openclassrooms.safetynet.controller.DTO.PhoneResponse;
import com.openclassrooms.safetynet.controller.DTO.ShortPersonResponse;
import com.openclassrooms.safetynet.domain.object.FireStation;
import com.openclassrooms.safetynet.domain.object.Person;
import com.openclassrooms.safetynet.model.DAO.FireStationDAO;
import com.openclassrooms.safetynet.model.DAO.PersonDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
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
  private FireStationDAO fireStationDAO;

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
    if (personDAO.getPersonsByFireStationNumber(stationNumber).isEmpty()) {
      throw new NoSuchElementException("there is no result for firestation " + stationNumber);
    } else {
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
  }

  public ChildrenByAddressResponse getChildrenByAddress(String address) {
    List<Person> personList = personDAO.findAll();
    List <String> addresses = new ArrayList<>();
    for (Person person : personList) {
      addresses.add(person.getAddress());
    }
    if (!addresses.contains(address)) {
      throw new NoSuchElementException("address " + address + " doesn't exist");
    } else {
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
  }

  public List<PhoneResponse> getPhonesByFireStation(String stationNumber) {
    if (personDAO.getPersonsByFireStationNumber(stationNumber).isEmpty()) {
      throw new NoSuchElementException("there is no result for firestation " + stationNumber);
    } else {
      List<Person> personByFireStationList = personDAO.getPersonsByFireStationNumber(stationNumber);
      List<PhoneResponse> phoneByFireStationList = new ArrayList<>();
      for (Person personByFireStation : personByFireStationList) {
        PhoneResponse phoneResponse = new PhoneResponse();
        phoneResponse.setPhone(personByFireStation.getPhone());
        phoneByFireStationList.add(phoneResponse);
      }
      return phoneByFireStationList;
    }
  }

  public PersonByAddressResponse getPersonsByAddress(String address) {
    if (personDAO.getPersonByAddress(address).isEmpty()) {
      throw new NoSuchElementException("there is no result for address " + address);
    } else {
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

  public List<List<List<NamePhoneAgeAndMedicalRecordResponse>>> getFamiliesByFireStations(List<Integer> stationNumberList) {
    List<FireStation> fireStationList = fireStationDAO.findAll();
    List<Integer> fireStationNumbers = new ArrayList<>();
    for (FireStation fireStation : fireStationList) {
      fireStationNumbers.add(Integer.valueOf(fireStation.getStationNumber()));
    }
    Set<String> addressSet = new HashSet<>();
    List<List<List<NamePhoneAgeAndMedicalRecordResponse>>> familiesByFireStations = new ArrayList<>();
    for (Integer stationNumber : stationNumberList) {
      List<List<NamePhoneAgeAndMedicalRecordResponse>> familiesByFireStationNumber = new ArrayList<>();
      List<Person> personsByFireStationNumber = personDAO.getPersonsByFireStationNumber(String.valueOf(stationNumber));
      if (!fireStationNumbers.contains(stationNumber)) {
        throw new NoSuchElementException("firestation number " + stationNumber + " doesn't exist");
      } else {
        for (Person personByFireStation : personsByFireStationNumber) {
          addressSet.add(personByFireStation.getAddress());
        }
        for (String address : addressSet) {
          List<Person> familyByFireStation = personDAO.getPersonByAddress(address);
          List<NamePhoneAgeAndMedicalRecordResponse> namePhoneAgeAndMedicalRecordsByAddressByFireStationNumber = new ArrayList<>();
          for (Person person : familyByFireStation) {
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/d/yyyy");
            String stringPersonBirthDate = personDAO.getPersonMedicalRecord(person.getId()).getBirthdate();
            LocalDate datePersonBirthDate = LocalDate.parse(stringPersonBirthDate, dateTimeFormatter);
            Period personAge = Period.between(datePersonBirthDate, currentDate);
            NamePhoneAgeAndMedicalRecordResponse namePhoneAgeAndMedicalRecordResponse = new NamePhoneAgeAndMedicalRecordResponse();
            namePhoneAgeAndMedicalRecordResponse.setLastName(person.getLastName());
            namePhoneAgeAndMedicalRecordResponse.setPhone(person.getPhone());
            namePhoneAgeAndMedicalRecordResponse.setAge(personAge.getYears());
            namePhoneAgeAndMedicalRecordResponse.setMedications(personDAO.getPersonMedicalRecord(person.getId()).getMedications());
            namePhoneAgeAndMedicalRecordResponse.setAllergies(personDAO.getPersonMedicalRecord(person.getId()).getAllergies());
            namePhoneAgeAndMedicalRecordsByAddressByFireStationNumber.add(namePhoneAgeAndMedicalRecordResponse);
          }
          familiesByFireStationNumber.add(namePhoneAgeAndMedicalRecordsByAddressByFireStationNumber);
        }
        familiesByFireStations.add(familiesByFireStationNumber);
        addressSet.clear();
      }
    }
    return familiesByFireStations;
  }

  public List<PersonInfoResponse> getPersonInfo(String lastName) {
    if (personDAO.getPersonByLastName(lastName).isEmpty()) {
      throw new NoSuchElementException("there is no result for last name " + lastName);
    } else {
      List<Person> persons = personDAO.getPersonByLastName(lastName);
      List<PersonInfoResponse> personInfoResponseList = new ArrayList<>();
      for (Person person : persons) {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM/d/yyyy");
        String stringPersonBirthDate = personDAO.getPersonMedicalRecord(person.getId()).getBirthdate();
        LocalDate datePersonBirthDate = LocalDate.parse(stringPersonBirthDate, dateTimeFormatter);
        Period personAge = Period.between(datePersonBirthDate, currentDate);
        PersonInfoResponse personInfoResponse = new PersonInfoResponse();
        personInfoResponse.setLastName(person.getLastName());
        personInfoResponse.setAddress(person.getAddress());
        personInfoResponse.setAge(personAge.getYears());
        personInfoResponse.setEmail(person.getEmail());
        personInfoResponse.setMedications(personDAO.getPersonMedicalRecord(person.getId()).getMedications());
        personInfoResponse.setAllergies(personDAO.getPersonMedicalRecord(person.getId()).getAllergies());
        personInfoResponseList.add(personInfoResponse);
      }
      return personInfoResponseList;
    }
  }

  public List<EmailResponse> getEmailsByCity(String city) {
    if (personDAO.getPersonByCity(city).isEmpty()) {
      throw new NoSuchElementException("there is no result for city " + city);
    } else {
      List<Person> personByCityList = personDAO.getPersonByCity(city);
      List<EmailResponse> emailByCityList = new ArrayList<>();
      for (Person personByCity : personByCityList) {
        EmailResponse emailResponse = new EmailResponse();
        emailResponse.setEmail(personByCity.getEmail());
        emailByCityList.add(emailResponse);
      }
      return emailByCityList;
    }
  }

}
