package com.openclassrooms.safetynet.service;

import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.repository.MedicalRecordRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import lombok.Data;

@Data
@Service
public class MedicalRecordService {

  @Autowired
  private MedicalRecordRepository medicalRecordRepository;

  public MedicalRecord getMedicalRecord(final Long id) {
    return medicalRecordRepository.findById(id).orElseThrow(() -> new NoSuchElementException("les données santé " + id + " n'existent pas"));
  }

  public Iterable<MedicalRecord> getMedicalRecords() {
    return medicalRecordRepository.findAll();
  }

  public void deleteMedicalRecord(final Long id) {
    if (medicalRecordRepository.existsById(id)) {
      medicalRecordRepository.deleteById(id);
    }
  }

  public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord) {
    if (!medicalRecordRepository.existsById(medicalRecord.getId())) {
      throw new EntityNotFoundException("medicalrecord " + medicalRecord.getId() + " doesn't exist");
    }
    return medicalRecordRepository.save(medicalRecord);
  }

  public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord) {
    if (medicalRecordRepository.existsById(medicalRecord.getId())) {
      throw new EntityExistsException("medicalrecord " + medicalRecord.getId() + " already exists");
    }
    return medicalRecordRepository.save(medicalRecord);
  }

}
