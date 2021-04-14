package com.openclassrooms.safetynet.model.DAO;

import com.openclassrooms.safetynet.domain.object.MedicalRecord;
import com.openclassrooms.safetynet.model.entity.MedicalRecordEntity;
import com.openclassrooms.safetynet.model.entity.PersonEntity;
import com.openclassrooms.safetynet.model.repository.MedicalRecordRepository;
import com.openclassrooms.safetynet.model.repository.PersonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public class MedicalRecordDAO {

  @Autowired
  private MedicalRecordRepository medicalRecordRepository;

  @Autowired
  private PersonRepository personRepository;

  public MedicalRecord findById(Long id) {
    MedicalRecordEntity medicalRecordEntity = medicalRecordRepository.findById(id).orElseThrow(() -> new NoSuchElementException("medicalRecord " + id + " doesn't exist"));
    MedicalRecord medicalRecord = new MedicalRecord();
    medicalRecord.setId(medicalRecordEntity.getId());
    medicalRecord.setFirstName(medicalRecordEntity.getPersonEntity().getFirstName());
    medicalRecord.setLastName(medicalRecordEntity.getPersonEntity().getLastName());
    medicalRecord.setBirthdate(medicalRecordEntity.getBirthdate());
    medicalRecord.setMedications(medicalRecordEntity.getMedications());
    medicalRecord.setAllergies(medicalRecordEntity.getAllergies());
    return medicalRecord;
  }

  public List<MedicalRecord> findAll() {
    List<MedicalRecordEntity> medicalRecordEntities =  StreamSupport.stream(medicalRecordRepository.findAll().spliterator(),false)
            .collect(Collectors.toList());
    return medicalRecordEntities.stream().map((medicalRecordEntity) -> {
      MedicalRecord medicalRecord = new MedicalRecord();
      medicalRecord.setId(medicalRecordEntity.getId());
      medicalRecord.setFirstName(medicalRecordEntity.getPersonEntity().getFirstName());
      medicalRecord.setLastName(medicalRecordEntity.getPersonEntity().getLastName());
      medicalRecord.setBirthdate(medicalRecordEntity.getBirthdate());
      medicalRecord.setMedications(medicalRecordEntity.getMedications());
      medicalRecord.setAllergies(medicalRecordEntity.getAllergies());
      return medicalRecord;
    }).collect(Collectors.toList());
  }

  public MedicalRecord updateMedicalRecord(Long id, MedicalRecord medicalRecord) {
    MedicalRecordEntity medicalRecordEntity = medicalRecordRepository.findById(id).orElseThrow(() -> new NoSuchElementException("medicalRecord " + id + " doesn't exist"));
    medicalRecordEntity.getPersonEntity().setFirstName(medicalRecord.getFirstName());
    medicalRecordEntity.getPersonEntity().setLastName(medicalRecord.getLastName());
    medicalRecordEntity.setBirthdate(medicalRecord.getBirthdate());
    medicalRecordEntity.setMedications(medicalRecord.getMedications());
    medicalRecordEntity.setAllergies(medicalRecord.getAllergies());
    medicalRecordRepository.save(medicalRecordEntity);
    return medicalRecord;
  }

  public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord) {
    PersonEntity personEntity = personRepository.findById(medicalRecord.getId()).orElseThrow(() -> new NoSuchElementException("person " + medicalRecord.getId() + " doesn't exist"));
    MedicalRecordEntity medicalRecordEntity = new MedicalRecordEntity();
    medicalRecordEntity.setPersonEntity(personEntity);
    medicalRecordEntity.setBirthdate(medicalRecord.getBirthdate());
    medicalRecordEntity.setMedications(medicalRecord.getMedications());
    medicalRecordEntity.setAllergies(medicalRecord.getAllergies());
    medicalRecordRepository.save(medicalRecordEntity);
    return medicalRecord;
  }

  public Boolean existById(Long id) {
    return medicalRecordRepository.existsById(id);
  }

  public void deleteById(Long id) {
    medicalRecordRepository.deleteById(id);
  }

}