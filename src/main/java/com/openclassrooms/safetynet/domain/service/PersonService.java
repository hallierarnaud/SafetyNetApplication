package com.openclassrooms.safetynet.domain.service;

import com.openclassrooms.safetynet.model.entity.FireStationEntity;
import com.openclassrooms.safetynet.model.entity.MedicalRecordEntity;
import com.openclassrooms.safetynet.model.entity.PersonEntity;
import com.openclassrooms.safetynet.controller.DTO.PersonMedicalRecordResponse;
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
import javax.persistence.EntityNotFoundException;

import lombok.Data;

@Data
@Service
public class PersonService {

  @Autowired
  private PersonRepository personRepository;

  @Autowired
  private MedicalRecordRepository medicalRecordRepository;

  @Autowired
  private FireStationRepository fireStationRepository;

  @Autowired
  private MapService mapService;

  public PersonEntity getPerson(final Long id) {
    return personRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
  }

  public List<PersonEntity> getPersons() {
    return StreamSupport.stream(personRepository.findAll().spliterator(),false)
            .collect(Collectors.toList());
  }

  public void deletePerson(final Long id) {
    if (!personRepository.existsById(id)) {
      throw new NoSuchElementException("person " + id + " doesn't exist");
    }
    personRepository.deleteById(id);
  }

  public PersonEntity updatePerson(final Long id, PersonMedicalRecordResponse personMedicalRecordResponse) {
    PersonEntity person = personRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("person " + id + " doesn't exist"));
    MedicalRecordEntity medicalRecord = medicalRecordRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
    mapService.updatePersonWithPersonMedicalDTO(person, medicalRecord, personMedicalRecordResponse);
    return personRepository.save(person);
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
