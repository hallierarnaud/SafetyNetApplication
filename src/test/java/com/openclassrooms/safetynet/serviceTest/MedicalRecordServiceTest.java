package com.openclassrooms.safetynet.serviceTest;

import com.openclassrooms.safetynet.controller.DTO.MedicalRecordAddOrUpdateRequest;
import com.openclassrooms.safetynet.domain.object.MedicalRecord;
import com.openclassrooms.safetynet.domain.object.Person;
import com.openclassrooms.safetynet.domain.service.MapService;
import com.openclassrooms.safetynet.domain.service.MedicalRecordService;
import com.openclassrooms.safetynet.model.DAO.MedicalRecordDAO;
import com.openclassrooms.safetynet.model.DAO.PersonDAO;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.persistence.EntityExistsException;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordServiceTest {

  @Mock
  private MedicalRecordDAO medicalRecordDAO;

  @Mock
  private PersonDAO personDAO;

  @Mock
  private MapService mapService;

  @InjectMocks
  private MedicalRecordService medicalRecordService;

  @Test
  public void addMedicalRecordTest_shouldReturnOk () {
    // GIVEN
    MedicalRecordAddOrUpdateRequest medicalRecordAddRequest = new MedicalRecordAddOrUpdateRequest();
    medicalRecordAddRequest.setId(1L);
    medicalRecordAddRequest.setBirthdate("01/01/2000");
    MedicalRecord medicalRecord = new MedicalRecord();
    Person person = new Person();
    when(personDAO.findById(medicalRecordAddRequest.getId())).thenReturn(person);
    when(medicalRecordDAO.addMedicalRecord(any(MedicalRecord.class))).thenReturn(medicalRecord);

    // WHEN
    MedicalRecord created = medicalRecordService.addMedicalRecord(medicalRecordAddRequest);

    // THEN
    assertEquals(created.getBirthdate(), medicalRecord.getBirthdate());
    verify(medicalRecordDAO).addMedicalRecord(any(MedicalRecord.class));
  }

  @Test
  public void addMedicalRecordTest_shouldReturnAlreadyExist () {
    // GIVEN
    MedicalRecordAddOrUpdateRequest medicalRecordAddRequest = new MedicalRecordAddOrUpdateRequest();
    medicalRecordAddRequest.setId(1L);

    // WHEN
    when(medicalRecordDAO.existById(anyLong())).thenReturn(TRUE);

    // THEN
    Throwable exception = assertThrows(EntityExistsException.class, () -> medicalRecordService.addMedicalRecord(medicalRecordAddRequest));
    assertEquals("medicalrecord 1 already exists", exception.getMessage());
  }

  @Test
  public void getMedicalRecords_shouldReturnOk () {
    // GIVEN
    List<MedicalRecord> medicalRecords = new ArrayList();
    medicalRecords.add(new MedicalRecord());
    when(medicalRecordDAO.findAll()).thenReturn(medicalRecords);

    // WHEN
    List<MedicalRecord> expected = medicalRecordService.getMedicalRecords();

    // THEN
    assertEquals(expected, medicalRecords);
    verify(medicalRecordDAO).findAll();
  }

  @Test
  public void deleteMedicalRecord_shouldReturnOk () {
    // GIVEN
    MedicalRecord medicalRecord = new MedicalRecord();
    medicalRecord.setId(1L);
    when(medicalRecordDAO.existById(anyLong())).thenReturn(TRUE);

    // WHEN
    medicalRecordService.deleteMedicalRecord(medicalRecord.getId());

    // THEN
    verify(medicalRecordDAO).deleteById(medicalRecord.getId());
  }

  @Test
  public void deleteMedicalRecord_shouldReturnNotFound () {
    // GIVEN
    MedicalRecord medicalRecord = new MedicalRecord();
    medicalRecord.setId(1L);

    // WHEN
    when(medicalRecordDAO.existById(anyLong())).thenReturn(FALSE);

    // THEN
    Throwable exception = assertThrows(NoSuchElementException.class, () -> medicalRecordService.deleteMedicalRecord(medicalRecord.getId()));
    assertEquals("medicalrecord 1 doesn't exist", exception.getMessage());
  }

  @Test
  public void updateMedicalRecord_shouldReturnOk () {
    // GIVEN
    MedicalRecordAddOrUpdateRequest medicalRecordUpdateRequest = new MedicalRecordAddOrUpdateRequest();
    medicalRecordUpdateRequest.setBirthdate("01/01/2000");
    MedicalRecord medicalRecord = new MedicalRecord();
    medicalRecord.setId(1L);
    when(medicalRecordDAO.findById(anyLong())).thenReturn(medicalRecord);
    when(medicalRecordDAO.updateMedicalRecord(anyLong(), any(MedicalRecord.class))).thenReturn(medicalRecord);

    // WHEN
    MedicalRecord updated = medicalRecordService.updateMedicalRecord(medicalRecord.getId(), medicalRecordUpdateRequest);

    // THEN
    assertEquals(medicalRecord.getBirthdate(), updated.getBirthdate());
    verify(medicalRecordDAO, times(2)).findById(medicalRecord.getId());
    verify(medicalRecordDAO).updateMedicalRecord(medicalRecord.getId(), medicalRecord);
  }

  @Test
  public void updateMedicalRecord_shouldReturnNotFound () {
    // GIVEN
    MedicalRecord medicalRecord = new MedicalRecord();
    medicalRecord.setId(1L);
    MedicalRecordAddOrUpdateRequest medicalRecordUpdateRequest = new MedicalRecordAddOrUpdateRequest();

    // THEN
    Throwable exception = assertThrows(NoSuchElementException.class, () -> medicalRecordService.updateMedicalRecord(medicalRecord.getId(), medicalRecordUpdateRequest));
    assertEquals("medicalrecord 1 doesn't exist", exception.getMessage());
  }

  @Test
  public void getMedicalRecord_shouldReturnOk () {
    // GIVEN
    MedicalRecord medicalRecord = new MedicalRecord();
    medicalRecord.setId(1L);
    when(medicalRecordDAO.findById(anyLong())).thenReturn(medicalRecord);

    // WHEN
    MedicalRecord expected = medicalRecordService.getMedicalRecord(medicalRecord.getId());

    // THEN
    assertEquals(expected, medicalRecord);
    verify(medicalRecordDAO, times(2)).findById(medicalRecord.getId());
  }

  @Test
  public void getMedicalRecord_shouldReturnNotFound () {
    // GIVEN
    MedicalRecord medicalRecord = new MedicalRecord();
    medicalRecord.setId(1L);

    // WHEN
    when(medicalRecordDAO.findById(anyLong())).thenReturn(null);

    // THEN
    assertThrows(NoSuchElementException.class, () -> medicalRecordService.getMedicalRecord(medicalRecord.getId()));
  }

}
