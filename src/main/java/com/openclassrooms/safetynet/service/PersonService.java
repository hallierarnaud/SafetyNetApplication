package com.openclassrooms.safetynet.service;

import com.openclassrooms.safetynet.model.FireStation;
import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.model.PersonMedicalRecordDTO;
import com.openclassrooms.safetynet.repository.FireStationRepository;
import com.openclassrooms.safetynet.repository.MedicalRecordRepository;
import com.openclassrooms.safetynet.repository.PersonRepository;

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

  public Person getPerson(final Long id) {
    return personRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
  }

  public List<Person> getPersons() {
    return StreamSupport.stream(personRepository.findAll().spliterator(),false)
            .collect(Collectors.toList());
  }

  public void deletePerson(final Long id) {
    if (!personRepository.existsById(id)) {
      throw new NoSuchElementException("person " + id + " doesn't exist");
    }
    personRepository.deleteById(id);
  }

  public Person updatePerson(final Long id, PersonMedicalRecordDTO personMedicalRecordDTO) {
    Person person = personRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("person " + id + " doesn't exist"));
    MedicalRecord medicalRecord = medicalRecordRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
    mapService.updatePersonWithPersonMedicalDTO(person, medicalRecord, personMedicalRecordDTO);
    return personRepository.save(person);
  }

  public Person addPerson(PersonMedicalRecordDTO personMedicalRecordDTO) {
    Person person = new Person();
    MedicalRecord medicalRecord = new MedicalRecord();
    person.setMedicalRecord(medicalRecord);
    medicalRecord.setPerson(person);
    mapService.updatePersonWithPersonMedicalDTO(person, medicalRecord, personMedicalRecordDTO);
    if (personRepository.existsById(personMedicalRecordDTO.getId())) {
      throw new EntityExistsException("person " + personMedicalRecordDTO.getId() + " already exists");
    }
    return personRepository.save(person);
  }

  public List<Person> findByLastNameLike(String lastName) {
    return personRepository.findByLastName(lastName);
  }

  public FireStation getPersonFireStation(Long id) {
    Person person = personRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException());
    String personAddress = person.getAddress();
    return fireStationRepository.findAll()
            .stream()
            .filter(fireStation -> fireStation.getAddresses().contains(personAddress))
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException());
  }

}
