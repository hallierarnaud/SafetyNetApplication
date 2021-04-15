package com.openclassrooms.safetynet.model.DAO;

import com.openclassrooms.safetynet.domain.object.FireStation;
import com.openclassrooms.safetynet.domain.object.MedicalRecord;
import com.openclassrooms.safetynet.domain.object.Person;
import com.openclassrooms.safetynet.model.entity.FireStationEntity;
import com.openclassrooms.safetynet.model.entity.MedicalRecordEntity;
import com.openclassrooms.safetynet.model.entity.PersonEntity;

import org.springframework.stereotype.Repository;

@Repository
public class MapDAO {

  public Person updatePersonWithPersonEntity (Person person, PersonEntity personEntity) {
    person.setId(personEntity.getId());
    person.setFirstName(personEntity.getFirstName());
    person.setLastName(personEntity.getLastName());
    person.setPhone(personEntity.getPhone());
    person.setZip(personEntity.getZip());
    person.setAddress(personEntity.getAddress());
    person.setCity(personEntity.getCity());
    person.setEmail(personEntity.getEmail());
    return person;
  }

  public PersonEntity updatePersonEntityWithPerson (PersonEntity personEntity, Person person) {
    personEntity.setPhone(person.getPhone());
    personEntity.setZip(person.getZip());
    personEntity.setAddress(person.getAddress());
    personEntity.setCity(person.getCity());
    personEntity.setEmail(person.getEmail());
    return personEntity;
  }

  public MedicalRecord updateMedicalRecordWithMedicalRecordEntity (MedicalRecord medicalRecord, MedicalRecordEntity medicalRecordEntity) {
    medicalRecord.setId(medicalRecordEntity.getId());
    medicalRecord.setFirstName(medicalRecordEntity.getPersonEntity().getFirstName());
    medicalRecord.setLastName(medicalRecordEntity.getPersonEntity().getLastName());
    medicalRecord.setBirthdate(medicalRecordEntity.getBirthdate());
    medicalRecord.setMedications(medicalRecordEntity.getMedications());
    medicalRecord.setAllergies(medicalRecordEntity.getAllergies());
    return medicalRecord;
  }

  public MedicalRecordEntity updateMedicalRecordEntityWithMedicalRecord (MedicalRecordEntity medicalRecordEntity, MedicalRecord medicalRecord) {
    medicalRecordEntity.setBirthdate(medicalRecord.getBirthdate());
    medicalRecordEntity.setMedications(medicalRecord.getMedications());
    medicalRecordEntity.setAllergies(medicalRecord.getAllergies());
    return medicalRecordEntity;
  }

  public FireStation updateFireStationWithFireStationEntity (FireStation fireStation, FireStationEntity fireStationEntity) {
    fireStation.setId(fireStationEntity.getId());
    fireStation.setAddresses(fireStationEntity.getAddresses());
    fireStation.setStationNumber(fireStationEntity.getStationNumber());
    return fireStation;
  }

  public FireStationEntity updateFireStationEntityWithFireStation (FireStationEntity fireStationEntity, FireStation fireStation) {
    fireStationEntity.setAddresses(fireStation.getAddresses());
    fireStationEntity.setStationNumber(fireStation.getStationNumber());
    return fireStationEntity;
  }

}
