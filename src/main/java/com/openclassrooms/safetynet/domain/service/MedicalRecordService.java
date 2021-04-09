package com.openclassrooms.safetynet.domain.service;

import com.openclassrooms.safetynet.model.entity.MedicalRecordEntity;
import com.openclassrooms.safetynet.controller.DTO.MedicalRecordResponse;
import com.openclassrooms.safetynet.model.repository.MedicalRecordRepository;
import com.openclassrooms.safetynet.model.repository.PersonRepository;

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

  @Autowired
  private PersonRepository personRepository;

  @Autowired
  private MapService mapService;

  public MedicalRecordEntity getMedicalRecord(final Long id) {
    return medicalRecordRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
  }

  public List<MedicalRecordEntity> getMedicalRecords() {
    return StreamSupport.stream(medicalRecordRepository.findAll().spliterator(), false)
            .collect(Collectors.toList());
  }

  public void deleteMedicalRecord(final Long id) {
    if (!medicalRecordRepository.existsById(id)) {
      throw new NoSuchElementException("medicalrecord " + id + " doesn't exist");
    }
    medicalRecordRepository.deleteById(id);
  }

  public MedicalRecordEntity updateMedicalRecord(final Long id, MedicalRecordResponse medicalRecordResponse) {
    MedicalRecordEntity medicalRecord = medicalRecordRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("medicalrecord " + id + " doesn't exist"));
    mapService.updateMedicalRecordWithMedicalRecordDTO(medicalRecord, medicalRecordResponse);
    return medicalRecordRepository.save(medicalRecord);
  }

  public MedicalRecordEntity addMedicalRecord(MedicalRecordResponse medicalRecordResponse) {
    MedicalRecordEntity medicalRecord = new MedicalRecordEntity();
    mapService.updateMedicalRecordWithMedicalRecordDTO(medicalRecord, medicalRecordResponse);
    if (medicalRecordRepository.existsById(medicalRecordResponse.getId())) {
      throw new EntityExistsException("medicalrecord " + medicalRecordResponse.getId() + " already exists");
    }
    return medicalRecordRepository.save(medicalRecord);
  }

}
