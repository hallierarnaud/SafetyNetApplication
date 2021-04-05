package com.openclassrooms.safetynet.service;

import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.model.MedicalRecordDTO;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.model.PersonDTO;
import com.openclassrooms.safetynet.model.PersonMedicalRecordDTO;

import org.springframework.stereotype.Service;

import lombok.Data;

@Data
@Service
public class MapService {

  public PersonMedicalRecordDTO convertPersonToPersonMedicalRecordDTO(Person person) {
    PersonMedicalRecordDTO personMedicalRecordDTO = new PersonMedicalRecordDTO();
    personMedicalRecordDTO.setId(person.getId());
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

  public Person updatePersonWithPersonMedicalDTO(Person person, MedicalRecord medicalRecord, PersonMedicalRecordDTO personMedicalRecordDTO) {
    person.setFirstName(personMedicalRecordDTO.getFirstName());
    person.setLastName(personMedicalRecordDTO.getLastName());
    person.setPhone(personMedicalRecordDTO.getPhone());
    person.setZip(personMedicalRecordDTO.getZip());
    person.setAddress(personMedicalRecordDTO.getAddress());
    person.setCity(personMedicalRecordDTO.getCity());
    person.setEmail(personMedicalRecordDTO.getEmail());
    medicalRecord.setBirthdate(personMedicalRecordDTO.getBirthdate());
    medicalRecord.setMedications(personMedicalRecordDTO.getMedications());
    medicalRecord.setAllergies(personMedicalRecordDTO.getAllergies());
    return person;
  }

  public MedicalRecordDTO convertMedicalRecordToMedicalRecordDTO(MedicalRecord medicalRecord) {
    MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO();
    medicalRecordDTO.setId(medicalRecord.getId());
    medicalRecordDTO.setBirthdate(medicalRecord.getBirthdate());
    medicalRecordDTO.setMedications(medicalRecord.getMedications());
    medicalRecordDTO.setAllergies(medicalRecord.getAllergies());
    return medicalRecordDTO;
  }

  public MedicalRecord updateMedicalRecordWithMedicalRecordDTO(MedicalRecord medicalRecord, MedicalRecordDTO medicalRecordDTO) {
    medicalRecord.setBirthdate(medicalRecordDTO.getBirthdate());
    medicalRecord.setMedications(medicalRecordDTO.getMedications());
    medicalRecord.setAllergies(medicalRecordDTO.getAllergies());
    return medicalRecord;
  }

  public PersonDTO convertPersonToPersonDTO(Person person) {
    PersonDTO personDTO = new PersonDTO();
    personDTO.setId(person.getId());
    personDTO.setFirstName(person.getFirstName());
    personDTO.setLastName(person.getLastName());
    personDTO.setPhone(person.getPhone());
    personDTO.setZip(person.getZip());
    personDTO.setAddress(person.getAddress());
    personDTO.setCity(person.getCity());
    personDTO.setEmail(person.getEmail());
    return personDTO;
  }

}
