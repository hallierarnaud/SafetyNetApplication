package com.openclassrooms.safetynet.service;

import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.repository.MedicalRecordRepository;

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
public class MedicalRecordService {

  @Autowired
  private MedicalRecordRepository medicalRecordRepository;

  public MedicalRecord getMedicalRecord(final Long id) {
    return medicalRecordRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
  }

  public List<MedicalRecord> getMedicalRecords() {
    return StreamSupport.stream(medicalRecordRepository.findAll().spliterator(), false)
            .collect(Collectors.toList());
  }

  public void deleteMedicalRecord(final Long id) {
    if (!medicalRecordRepository.existsById(id)) {
      throw new NoSuchElementException();
    }
    medicalRecordRepository.deleteById(id);
  }

  public MedicalRecord updateMedicalRecord(final Long id, MedicalRecord medicalRecord) {
    if (!medicalRecordRepository.existsById(id)) {
      throw new EntityNotFoundException("medicalrecord " + id + " doesn't exist");
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
