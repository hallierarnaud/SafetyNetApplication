package com.openclassrooms.safetynet.domain.service;

import com.openclassrooms.safetynet.controller.DTO.MedicalRecordAddOrUpdateRequest;
import com.openclassrooms.safetynet.domain.object.MedicalRecord;
import com.openclassrooms.safetynet.domain.object.Person;
import com.openclassrooms.safetynet.model.DAO.MedicalRecordDAO;
import com.openclassrooms.safetynet.model.DAO.PersonDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.persistence.EntityExistsException;

import lombok.Data;

@Data
@Service
public class MedicalRecordService {

  @Autowired
  private MedicalRecordDAO medicalRecordDAO;

  @Autowired
  private PersonDAO personDAO;

  @Autowired
  private MapService mapService;

  public MedicalRecord getMedicalRecord(final Long id) {
    if (medicalRecordDAO.findById(id) == null) {
      throw new NoSuchElementException("medicalRecord " + id + " doesn't exist");
    }
    return medicalRecordDAO.findById(id);
  }

  public List<MedicalRecord> getMedicalRecords() {
    return StreamSupport.stream(medicalRecordDAO.findAll().spliterator(), false)
            .collect(Collectors.toList());
  }

  public void deleteMedicalRecord(final Long id) {
    if (!medicalRecordDAO.existById(id)) {
      throw new NoSuchElementException("medicalrecord " + id + " doesn't exist");
    }
    medicalRecordDAO.deleteById(id);
  }

  public MedicalRecord updateMedicalRecord(final Long id, MedicalRecordAddOrUpdateRequest medicalRecordUpdateRequest) {
    if (medicalRecordDAO.findById(id) == null) {
      throw new NoSuchElementException("medicalRecord " + id + " doesn't exist");
    }
    MedicalRecord medicalRecord = medicalRecordDAO.findById(id);
    medicalRecord.setId(id);
    mapService.updateMedicalRecordWithMedicalRecordRequest(medicalRecord, medicalRecordUpdateRequest);
    return medicalRecordDAO.updateMedicalRecord(id, medicalRecord);
  }

  public MedicalRecord addMedicalRecord(MedicalRecordAddOrUpdateRequest medicalRecordAddRequest) {
    if (medicalRecordDAO.existById(medicalRecordAddRequest.getId())) {
      throw new EntityExistsException("medicalrecord " + medicalRecordAddRequest.getId() + " already exists");
    }
    MedicalRecord medicalRecord = new MedicalRecord();
    medicalRecord.setId(medicalRecordAddRequest.getId());
    if (personDAO.findById(medicalRecordAddRequest.getId()) == null) {
      throw new NoSuchElementException("person " + medicalRecordAddRequest.getId() + "doesn't exist");
    }
    Person person = personDAO.findById(medicalRecordAddRequest.getId());
    medicalRecord.setFirstName(person.getFirstName());
    medicalRecord.setLastName(person.getLastName());
    mapService.updateMedicalRecordWithMedicalRecordRequest(medicalRecord, medicalRecordAddRequest);
    return medicalRecordDAO.addMedicalRecord(medicalRecord);
  }

}
