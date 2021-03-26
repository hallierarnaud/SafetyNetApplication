package com.openclassrooms.safetynet.service;

import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.model.PersonMedicalRecordDTO;

import org.springframework.stereotype.Service;

import lombok.Data;

@Data
@Service
public class MapService {

  public PersonMedicalRecordDTO convertToPersonMedicalRecordDTO(Person person) {
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
