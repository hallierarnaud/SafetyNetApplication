package com.openclassrooms.safetynet.domain.service;

import com.openclassrooms.safetynet.controller.DTO.PersonAddOrUpdateRequest;
import com.openclassrooms.safetynet.domain.object.Person;
import com.openclassrooms.safetynet.model.DAO.PersonDAO;
import com.openclassrooms.safetynet.model.entity.FireStationEntity;
import com.openclassrooms.safetynet.model.entity.PersonEntity;
import com.openclassrooms.safetynet.model.repository.FireStationRepository;
import com.openclassrooms.safetynet.model.repository.MedicalRecordRepository;
import com.openclassrooms.safetynet.model.repository.PersonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.persistence.EntityExistsException;

import lombok.Data;

@Data
@Service
public class PersonService {

  // TODO : personRepository pourra être supprimer une fois que le personDAO fera le job
  @Autowired
  private PersonRepository personRepository;

  @Autowired
  private PersonDAO personDAO;

  @Autowired
  private MedicalRecordRepository medicalRecordRepository;

  @Autowired
  private FireStationRepository fireStationRepository;

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

  public void deletePerson(final Long id) {
    if (!personDAO.existById(id)) {
      throw new NoSuchElementException("person " + id + " doesn't exist");
    }
    personDAO.deleteById(id);
  }

  /*public PersonEntity updatePerson(final Long id, PersonUpdateRequest personUpdateRequest) {
    //PersonEntity person = personRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("person " + id + " doesn't exist"));
    //MedicalRecordEntity medicalRecord = medicalRecordRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
    Person person = personDAO.findById(id);
    person.setFirstName(personUpdateRequest.getFirstName());
    person.setLastName(personUpdateRequest.getLastName());
    return personDAO.update(person);
  }*/

  public Person updateSimplePerson(final Long id, PersonAddOrUpdateRequest personUpdateRequest) {
    if (personDAO.findById(id) == null) {
      throw new NoSuchElementException("person " + id + " doesn't exist");
    }
    Person person = personDAO.findById(id);
    person.setId(id);
    person.setFirstName(personUpdateRequest.getFirstName());
    person.setLastName(personUpdateRequest.getLastName());
    person.setPhone(personUpdateRequest.getPhone());
    person.setZip(personUpdateRequest.getZip());
    person.setAddress(personUpdateRequest.getAddress());
    person.setCity(personUpdateRequest.getCity());
    person.setEmail(personUpdateRequest.getEmail());
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
    person.setPhone(personAddRequest.getPhone());
    person.setZip(personAddRequest.getZip());
    person.setAddress(personAddRequest.getAddress());
    person.setCity(personAddRequest.getCity());
    person.setEmail(personAddRequest.getEmail());
    return personDAO.addSimplePerson(person);
  }

  public List<PersonEntity> findByLastNameLike(String lastName) {
    return personRepository.findByLastName(lastName);
  }

  public FireStationEntity getPersonFireStation(Long id) {
    PersonEntity person = personRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException());
    String personAddress = person.getAddress();
    return fireStationRepository.findAll()
            .stream()
            .filter(fireStation -> fireStation.getAddresses().contains(personAddress))
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException());
  }

}
