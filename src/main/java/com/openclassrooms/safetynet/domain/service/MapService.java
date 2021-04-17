package com.openclassrooms.safetynet.domain.service;

import com.openclassrooms.safetynet.controller.DTO.FireStationAddOrUpdateRequest;
import com.openclassrooms.safetynet.controller.DTO.FireStationResponse;
import com.openclassrooms.safetynet.controller.DTO.MedicalRecordAddOrUpdateRequest;
import com.openclassrooms.safetynet.controller.DTO.MedicalRecordResponse;
import com.openclassrooms.safetynet.controller.DTO.PersonAddOrUpdateRequest;
import com.openclassrooms.safetynet.controller.DTO.PersonResponse;
import com.openclassrooms.safetynet.domain.object.FireStation;
import com.openclassrooms.safetynet.domain.object.MedicalRecord;
import com.openclassrooms.safetynet.domain.object.Person;

import org.springframework.stereotype.Service;

@Service
public class MapService {

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

  public Person updatePersonWithPersonRequest(Person person, PersonAddOrUpdateRequest personAddOrUpdateRequest) {
    person.setPhone(personAddOrUpdateRequest.getPhone());
    person.setZip(personAddOrUpdateRequest.getZip());
    person.setAddress(personAddOrUpdateRequest.getAddress());
    person.setCity(personAddOrUpdateRequest.getCity());
    person.setEmail(personAddOrUpdateRequest.getEmail());
    return person;
  }

  public MedicalRecordResponse convertMedicalRecordToMedicalRecordResponse(MedicalRecord medicalRecord) {
    MedicalRecordResponse medicalRecordResponse = new MedicalRecordResponse();
    medicalRecordResponse.setId(medicalRecord.getId());
    medicalRecordResponse.setFirstName(medicalRecord.getFirstName());
    medicalRecordResponse.setLastName(medicalRecord.getLastName());
    medicalRecordResponse.setBirthdate(medicalRecord.getBirthdate());
    medicalRecordResponse.setMedications(medicalRecord.getMedications());
    medicalRecordResponse.setAllergies(medicalRecord.getAllergies());
    return medicalRecordResponse;
  }

  public MedicalRecord updateMedicalRecordWithMedicalRecordRequest(MedicalRecord medicalRecord, MedicalRecordAddOrUpdateRequest medicalRecordAddOrUpdateRequest) {
    medicalRecord.setBirthdate(medicalRecordAddOrUpdateRequest.getBirthdate());
    medicalRecord.setMedications(medicalRecordAddOrUpdateRequest.getMedications());
    medicalRecord.setAllergies(medicalRecordAddOrUpdateRequest.getAllergies());
    return medicalRecord;
  }

  public FireStationResponse convertFireStationToFireStationResponse(FireStation fireStation) {
    FireStationResponse fireStationResponse = new FireStationResponse();
    fireStationResponse.setId(fireStation.getId());
    fireStationResponse.setAddresses(fireStation.getAddresses());
    fireStationResponse.setStationNumber(fireStation.getStationNumber());
    return fireStationResponse;
  }

  public FireStation updateFireStationWithFireStationRequest(FireStation fireStation, FireStationAddOrUpdateRequest fireStationAddOrUpdateRequest) {
    fireStation.setAddresses(fireStationAddOrUpdateRequest.getAddresses());
    fireStation.setStationNumber(fireStationAddOrUpdateRequest.getStationNumber());
    return fireStation;
  }

}
