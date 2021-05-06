package com.openclassrooms.safetynet.controller.endpoint;

import com.openclassrooms.safetynet.controller.DTO.ChildrenByAddressResponse;
import com.openclassrooms.safetynet.controller.DTO.EmailResponse;
import com.openclassrooms.safetynet.controller.DTO.NamePhoneAgeAndMedicalRecordResponse;
import com.openclassrooms.safetynet.controller.DTO.PersonAddOrUpdateRequest;
import com.openclassrooms.safetynet.controller.DTO.PersonByAddressResponse;
import com.openclassrooms.safetynet.controller.DTO.PersonByFireStationResponse;
import com.openclassrooms.safetynet.controller.DTO.PersonInfoResponse;
import com.openclassrooms.safetynet.controller.DTO.PersonResponse;
import com.openclassrooms.safetynet.controller.DTO.PhoneResponse;
import com.openclassrooms.safetynet.domain.service.MapService;
import com.openclassrooms.safetynet.domain.service.PersonService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.persistence.EntityExistsException;

import lombok.extern.slf4j.Slf4j;

/**
 * a class to perform CRUD operations on person and business treatments
 */
@Slf4j
@RestController
public class PersonController {

  private static Logger logger = LoggerFactory.getLogger(PersonController.class);

  @Autowired
  private PersonService personService;

  @Autowired
  private MapService mapService;

  /**
   * @return a list of all persons in the database
   */
  @GetMapping("/persons")
  public List<PersonResponse> getPersons() {
    return personService.getPersons().stream().map(p -> mapService.convertPersonToPersonResponse(p)).collect(Collectors.toList());
  }

  /**
   * @param id a person's id
   * @return a person corresponding to the id
   */
  @GetMapping("/persons/{id}")
  public PersonResponse getPersonById(@PathVariable("id") long id) {
    try {
      return mapService.convertPersonToPersonResponse(personService.getPerson(id));
    } catch (NoSuchElementException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "person " + id + " doesn't exist");
    }
  }

  /**
   * @param personAddRequest a person defined by his attributes
   * @return add the person to the database
   */
  @PostMapping("/persons")
  public PersonResponse addSimplePerson(@RequestBody PersonAddOrUpdateRequest personAddRequest) {
    try {
      return mapService.convertPersonToPersonResponse(personService.addSimplePerson(personAddRequest));
    } catch (EntityExistsException e) {
      throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "person " + personAddRequest.getId() + " already exists");
    }
  }

  /**
   * @param firstName first name of a person
   * @param lastName last name of a person
   */
  @DeleteMapping("/persons/{firstName}/{lastName}")
  public void deletePersonByFirstAndLastName(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName) {
    try {
      personService.deletePerson(firstName, lastName);
      throw new ResponseStatusException(HttpStatus.OK, firstName + " " + lastName + " was deleted");
    } catch (NoSuchElementException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, firstName + " " + lastName + " doesn't exist");
    }
  }

  /**
   * @param id a person's id
   * @param personUpdateRequest a person defined by his attributes
   * @return update the person in the database
   */
  @PutMapping("/persons/{id}")
  public PersonResponse updateSimplePerson(@PathVariable("id") long id, @RequestBody PersonAddOrUpdateRequest personUpdateRequest) {
    try {
      return mapService.convertPersonToPersonResponse(personService.updateSimplePerson(id, personUpdateRequest));
    } catch (NoSuchElementException e) {
      throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "person " + id + " doesn't exist");
    }
  }

  /**
   * @param stationNumber a number identifying a fire station
   * @return a list of the persons covered by the fire station and the number of children and adults
   */
  @GetMapping("/firestation")
  public PersonByFireStationResponse getPersonsByFireStationNumber(@RequestParam("stationNumber") String stationNumber) {
    logger.info("Query : localhost:9000/firestation?stationNumber={}", stationNumber);
    try {
      logger.info("Response : persons covered by firestation {} were found", stationNumber);
      return personService.getPersonsByFireStation(stationNumber);
    } catch (NoSuchElementException e) {
      logger.error("Response : there is no result for firestation {}", stationNumber);
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "there is no result for firestation " + stationNumber);
    }
  }

  /**
   * @param address a person's address
   * @return a list of children (persons under 18) living at the address
   */
  @GetMapping("/childAlert")
  public ChildrenByAddressResponse getChildrenByAddress(@RequestParam("address") String address) {
    logger.info("Query : localhost:9000/childAlert?address={}", address);
    try {
      logger.info("Response : persons living at {} were found", address);
      return personService.getChildrenByAddress(address);
    } catch (NoSuchElementException e) {
      logger.error("Response : address {} doesn't exist", address);
      throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "address " + address + " doesn't exist");
    }
  }

  /**
   * @param stationNumber a number identifying a fire station
   * @return a phone number list of persons covered by the fire station
   */
  @GetMapping("/phoneAlert")
  public List<PhoneResponse> getPhonesByFireStation(@RequestParam("stationNumber") String stationNumber) {
    logger.info("Query : localhost:9000/phoneAlert?firestation={}", stationNumber);
    try {
      logger.info("Response : person's phone numbers covered by firestation {} were found", stationNumber);
      return personService.getPhonesByFireStation(stationNumber);
    } catch (NoSuchElementException e) {
      logger.error("Response : there is no result for firestation {}", stationNumber);
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "there is no result for firestation " + stationNumber);
    }
  }

  /**
   * @param address a person's address
   * @return a list of persons living at the address with their medical record and their fire station number
   */
  @GetMapping("/fire")
  public PersonByAddressResponse getPersonsByAddress(@RequestParam("address") String address) {
    logger.info("Query : localhost:9000/fire?address=<{}", address);
    try {
      logger.info("Response : persons living at {} were found", address);
      return personService.getPersonsByAddress(address);
    } catch (NoSuchElementException e) {
      logger.error("Response : there is no result for address {}", address);
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "there is no result for address " + address);
    }
  }

  /**
   * @param stationNumberList a list of fire station numbers
   * @return lists of persons covered by each fire station with their medical record and grouped by address
   */
  @GetMapping("/flood/stations")
  public List<List<List<NamePhoneAgeAndMedicalRecordResponse>>> getFamiliesByFireStation(@RequestParam("stations") List<Integer> stationNumberList) {
    logger.info("Query : localhost:9000/flood/stations?stations={}", stationNumberList);
    try {
      logger.info("Response : families covered by firestation(s) {} were found", stationNumberList);
      return personService.getFamiliesByFireStations(stationNumberList);
    } catch (NoSuchElementException e) {
      logger.error("Response : firestation number {} doesn't exist", stationNumberList);
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "there is a firestation number in the list " + stationNumberList + " which doesn't exist");
    }
  }

  /**
   * @param lastName last name of a person
   * @return a list of persons with the same last name with their medical record
   */
  @GetMapping("/personInfo")
  public List<PersonInfoResponse> getPersonsInfo(@RequestParam("lastName") String lastName) {
    logger.info("Query : localhost:9000/personInfo?lastName={}", lastName);
    try {
      logger.info("Response : person(s) with last name {} were found", lastName);
      return personService.getPersonInfo(lastName);
    } catch (NoSuchElementException e) {
      logger.error("Response : there is no result for last name {}", lastName);
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "there is no result for last name " + lastName);
    }
  }

  /**
   * @param city a city name
   * @return a list of all the emails of the city
   */
  @GetMapping("/communityEmail")
  public List<EmailResponse> getEmailsByCity(@RequestParam("city") String city) {
    logger.info("Query : localhost:9000/communityEmail?city={}", city);
    try {
      logger.info("Response : emails of city {} were found", city);
      return personService.getEmailsByCity(city);
    } catch (NoSuchElementException e) {
      logger.error("Response : there is no result for city {}", city);
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "there is no result for city " + city);
    }
  }

}
