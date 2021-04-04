package com.openclassrooms.safetynet.service;

import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.model.MedicalRecordDTO;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.repository.MedicalRecordRepository;
import com.openclassrooms.safetynet.repository.PersonRepository;

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

  public MedicalRecord updateMedicalRecord(final Long id, MedicalRecordDTO medicalRecordDTO) {
    MedicalRecord medicalRecord = medicalRecordRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
    mapService.updateMedicalRecordWithMedicalRecordDTO(medicalRecord, medicalRecordDTO);
    return medicalRecordRepository.save(medicalRecord);
  }

  public MedicalRecord addMedicalRecord(MedicalRecord medicalRecord) {
    if (medicalRecordRepository.existsById(medicalRecord.getId())) {
      throw new EntityExistsException("medicalrecord " + medicalRecord.getId() + " already exists");
    }
    return medicalRecordRepository.save(medicalRecord);
  }

}
