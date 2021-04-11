package com.openclassrooms.safetynet.domain.service;

import com.openclassrooms.safetynet.controller.DTO.PersonMedicalRecordResponse;
import com.openclassrooms.safetynet.controller.DTO.PersonUpdateRequest;
import com.openclassrooms.safetynet.domain.object.Person;
import com.openclassrooms.safetynet.model.DAO.PersonDAO;
import com.openclassrooms.safetynet.model.entity.FireStationEntity;
import com.openclassrooms.safetynet.model.entity.MedicalRecordEntity;
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

  public PersonEntity getPerson(final Long id) {
    return personRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
  }

  public List<Person> getPersons() {
    return StreamSupport.stream(personDAO.findAll().spliterator(),false)
            .collect(Collectors.toList());
  }

  public void deletePerson(final Long id) {
    if (!personRepository.existsById(id)) {
      throw new NoSuchElementException("person " + id + " doesn't exist");
    }
    personRepository.deleteById(id);
  }

  /*public PersonEntity updatePerson(final Long id, PersonUpdateRequest personUpdateRequest) {
    //PersonEntity person = personRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("person " + id + " doesn't exist"));
    //MedicalRecordEntity medicalRecord = medicalRecordRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
    Person person = personDAO.findById(id);
    person.setFirstName(personUpdateRequest.getFirstName());
    person.setLastName(personUpdateRequest.getLastName());
    return personDAO.update(person);
  }*/

  public Person updateSimplePerson(long id, PersonUpdateRequest personUpdateRequest) {
    Person person = personDAO.findById(id);
    person.setId(personUpdateRequest.getId());
    person.setFirstName(personUpdateRequest.getFirstName());
    person.setLastName(personUpdateRequest.getLastName());
    person.setPhone(personUpdateRequest.getPhone());
    person.setZip(personUpdateRequest.getZip());
    person.setAddress(personUpdateRequest.getAddress());
    person.setCity(personUpdateRequest.getCity());
    person.setEmail(personUpdateRequest.getEmail());
    return personDAO.updateSimplePerson(person);
  }

  public PersonEntity addPerson(PersonMedicalRecordResponse personMedicalRecordResponse) {
    PersonEntity person = new PersonEntity();
    MedicalRecordEntity medicalRecord = new MedicalRecordEntity();
    person.setMedicalRecord(medicalRecord);
    medicalRecord.setPerson(person);
    mapService.updatePersonWithPersonMedicalDTO(person, medicalRecord, personMedicalRecordResponse);
    if (personRepository.existsById(personMedicalRecordResponse.getId())) {
      throw new EntityExistsException("person " + personMedicalRecordResponse.getId() + " already exists");
    }
    return personRepository.save(person);
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
