package com.openclassrooms.safetynet.domain.service;

import com.openclassrooms.safetynet.controller.DTO.MedicalRecordResponse;
import com.openclassrooms.safetynet.controller.DTO.PersonMedicalRecordResponse;
import com.openclassrooms.safetynet.controller.DTO.PersonResponse;
import com.openclassrooms.safetynet.domain.object.Person;
import com.openclassrooms.safetynet.model.entity.MedicalRecordEntity;
import com.openclassrooms.safetynet.model.entity.PersonEntity;

import org.springframework.stereotype.Service;

import lombok.Data;

@Data
@Service
public class MapService {

  public PersonMedicalRecordResponse convertPersonToPersonMedicalRecordDTO(PersonEntity person) {
    PersonMedicalRecordResponse personMedicalRecordResponse = new PersonMedicalRecordResponse();
    personMedicalRecordResponse.setId(person.getId());
    personMedicalRecordResponse.setFirstName(person.getFirstName());
    personMedicalRecordResponse.setLastName(person.getLastName());
    personMedicalRecordResponse.setPhone(person.getPhone());
    personMedicalRecordResponse.setZip(person.getZip());
    personMedicalRecordResponse.setAddress(person.getAddress());
    personMedicalRecordResponse.setCity(person.getCity());
    personMedicalRecordResponse.setEmail(person.getEmail());
    MedicalRecordEntity medicalRecord = person.getMedicalRecord();
    personMedicalRecordResponse.setBirthdate(medicalRecord.getBirthdate());
    personMedicalRecordResponse.setMedications(medicalRecord.getMedications());
    personMedicalRecordResponse.setAllergies(medicalRecord.getAllergies());
    return personMedicalRecordResponse;
  }

  public PersonEntity updatePersonWithPersonMedicalDTO(PersonEntity person, MedicalRecordEntity medicalRecord, PersonMedicalRecordResponse personMedicalRecordResponse) {
    person.setFirstName(personMedicalRecordResponse.getFirstName());
    person.setLastName(personMedicalRecordResponse.getLastName());
    person.setPhone(personMedicalRecordResponse.getPhone());
    person.setZip(personMedicalRecordResponse.getZip());
    person.setAddress(personMedicalRecordResponse.getAddress());
    person.setCity(personMedicalRecordResponse.getCity());
    person.setEmail(personMedicalRecordResponse.getEmail());
    medicalRecord.setBirthdate(personMedicalRecordResponse.getBirthdate());
    medicalRecord.setMedications(personMedicalRecordResponse.getMedications());
    medicalRecord.setAllergies(personMedicalRecordResponse.getAllergies());
    return person;
  }

  public MedicalRecordResponse convertMedicalRecordToMedicalRecordDTO(MedicalRecordEntity medicalRecord) {
    MedicalRecordResponse medicalRecordResponse = new MedicalRecordResponse();
    medicalRecordResponse.setId(medicalRecord.getId());
    medicalRecordResponse.setBirthdate(medicalRecord.getBirthdate());
    medicalRecordResponse.setMedications(medicalRecord.getMedications());
    medicalRecordResponse.setAllergies(medicalRecord.getAllergies());
    return medicalRecordResponse;
  }

  public MedicalRecordEntity updateMedicalRecordWithMedicalRecordDTO(MedicalRecordEntity medicalRecord, MedicalRecordResponse medicalRecordResponse) {
    medicalRecord.setBirthdate(medicalRecordResponse.getBirthdate());
    medicalRecord.setMedications(medicalRecordResponse.getMedications());
    medicalRecord.setAllergies(medicalRecordResponse.getAllergies());
    return medicalRecord;
  }

  /*public PersonResponse convertPersonToPersonDTO(PersonEntity person) {
    PersonResponse personResponse = new PersonResponse();
    personResponse.setId(person.getId());
    personResponse.setFirstName(person.getFirstName());
    personResponse.setLastName(person.getLastName());
    personResponse.setPhone(person.getPhone());
    personResponse.setZip(person.getZip());
    personResponse.setAddress(person.getAddress());
    personResponse.setCity(person.getCity());
    personResponse.setEmail(person.getEmail());
    return personResponse;
  }*/

  public PersonResponse convertPersonToPersonResponse(Person person) {
    PersonResponse personResponse = new PersonResponse();
    personResponse.setId(person.getId());
    personResponse.setFirstName(person.getFirstName());
    personResponse.setLastName(person.getLastName());
    personResponse.setPhone(person.getPhone());
    personResponse.setZip(person.getZip());
    personResponse.setAddress(person.getAddress());
    personResponse.setCity(person.getCity());
    personResponse.setEmail(person.getEmail());
    return personResponse;
  }
}
