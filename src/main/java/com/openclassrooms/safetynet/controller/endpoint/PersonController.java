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

@RestController
public class PersonController {

  @Autowired
  private PersonService personService;

  @Autowired
  private MapService mapService;

  @GetMapping("/persons")
  public List<PersonResponse> getPersons() {
    return personService.getPersons().stream().map(p -> mapService.convertPersonToPersonResponse(p)).collect(Collectors.toList());
  }

  @GetMapping("/persons/{id}")
  public PersonResponse getPersonById(@PathVariable("id") long id) {
    try {
      return mapService.convertPersonToPersonResponse(personService.getPerson(id));
    } catch (NoSuchElementException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "person " + id + " doesn't exist");
    }
  }

  @PostMapping("/persons")
  public PersonResponse addSimplePerson(@RequestBody PersonAddOrUpdateRequest personAddRequest) {
    try {
      return mapService.convertPersonToPersonResponse(personService.addSimplePerson(personAddRequest));
    } catch (EntityExistsException e) {
      throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "person " + personAddRequest.getId() + " already exists");
    }
  }

  @DeleteMapping("/persons/{firstName}/{lastName}")
  public void deletePersonByFirstAndLastName(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName) {
    try {
      personService.deletePerson(firstName, lastName);
      throw new ResponseStatusException(HttpStatus.OK, firstName + " " + lastName + " was deleted");
    } catch (NoSuchElementException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, firstName + " " + lastName + " doesn't exist");
    }
  }

  @PutMapping("/persons/{id}")
  public PersonResponse updateSimplePerson(@PathVariable("id") long id, @RequestBody PersonAddOrUpdateRequest personUpdateRequest) {
    try {
      return mapService.convertPersonToPersonResponse(personService.updateSimplePerson(id, personUpdateRequest));
    } catch (NoSuchElementException e) {
      throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "person " + id + " doesn't exist");
    }
  }

  @GetMapping("/firestation")
  public PersonByFireStationResponse getPersonsByFireStationNumber(@RequestParam("stationNumber") String stationNumber) {
    try {
      return personService.getPersonsByFireStation(stationNumber);
    } catch (NoSuchElementException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "there is no result for firestation " + stationNumber);
    }
  }

  @GetMapping("/childAlert")
  public ChildrenByAddressResponse getChildrenByAddress(@RequestParam("address") String address) {
    try {
      return personService.getChildrenByAddress(address);
    } catch (NoSuchElementException e) {
      throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "address " + address + " doesn't exist");
    }
  }

  @GetMapping("/phoneAlert")
  public List<PhoneResponse> getPhonesByFireStation(@RequestParam("stationNumber") String stationNumber) {
    try {
      return personService.getPhonesByFireStation(stationNumber);
    } catch (NoSuchElementException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "there is no result for firestation " + stationNumber);
    }
  }

  @GetMapping("/fire")
  public PersonByAddressResponse getPersonsByAddress(@RequestParam("address") String address) {
    try {
      return personService.getPersonsByAddress(address);
    } catch (NoSuchElementException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "there is no result for address " + address);
    }
  }

  @GetMapping("/flood/stations")
  public List<List<List<NamePhoneAgeAndMedicalRecordResponse>>> getFamiliesByFireStation(@RequestParam("stations") List<Integer> stationNumberList) {
    try {
      return personService.getFamiliesByFireStations(stationNumberList);
    } catch (NoSuchElementException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "there is a firestation number in the list " + stationNumberList + " which doesn't exist");
    }
  }

  @GetMapping("/personInfo")
  public List<PersonInfoResponse> getPersonsInfo(@RequestParam("lastName") String lastName) {
    try {
      return personService.getPersonInfo(lastName);
    } catch (NoSuchElementException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "there is no result for last name " + lastName);
    }
  }

  @GetMapping("/communityEmail")
  public List<EmailResponse> getEmailsByCity(@RequestParam("city") String city) {
    try {
      return personService.getEmailsByCity(city);
    } catch (NoSuchElementException e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "there is no result for city " + city);
    }
  }

}
