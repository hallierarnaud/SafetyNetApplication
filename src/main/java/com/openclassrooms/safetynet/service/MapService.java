package com.openclassrooms.safetynet.service;

import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.model.PersonMedicalRecordDTO;
import com.openclassrooms.safetynet.repository.PersonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;

@Data
@Service
public class MapService {

  @Autowired
  private PersonRepository personRepository;

  public List<PersonMedicalRecordDTO> getAllPersonsMedicalRecord() {
    return ((List<Person>) personRepository
            .findAll())
            .stream()
            .map(this::convertToPersonMedicalRecordDTO).collect(Collectors.toList());
  }

  private PersonMedicalRecordDTO convertToPersonMedicalRecordDTO(Person person) {
    PersonMedicalRecordDTO personMedicalRecordDTO = new PersonMedicalRecordDTO();
    personMedicalRecordDTO.setFirstName(person.getFirstName());
    personMedicalRecordDTO.setLastName(person.getLastName());
    personMedicalRecordDTO.setPhone(person.getPhone());
    personMedicalRecordDTO.setZip(person.getZip());
    personMedicalRecordDTO.setAddress(person.getAddress());
    personMedicalRecordDTO.setCity(person.getCity());
    personMedicalRecordDTO.setEmail(person.getEmail());
    MedicalRecord medicalRecord = person.getMedicalRecord();
    personMedicalRecordDTO.setBirthdate(medicalRecord.getBirthdate());
    personMedicalRecordDTO.setMedications(medicalRecord.getMedications());
    personMedicalRecordDTO.setAllergies(medicalRecord.getAllergies());
    return personMedicalRecordDTO;
  }

}
