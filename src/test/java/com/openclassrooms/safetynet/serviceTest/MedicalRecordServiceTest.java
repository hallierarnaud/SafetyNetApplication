package com.openclassrooms.safetynet.serviceTest;

import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.model.MedicalRecordDTO;
import com.openclassrooms.safetynet.repository.MedicalRecordRepository;
import com.openclassrooms.safetynet.service.MapService;
import com.openclassrooms.safetynet.service.MedicalRecordService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordServiceTest {

  @Mock
  private MedicalRecordRepository medicalRecordRepository;

  @Mock
  private MapService mapService;

  @InjectMocks
  private MedicalRecordService medicalRecordService;

  @Test
  public void addMedicalRecordTest_shouldReturnOk () {
    // GIVEN
    MedicalRecord medicalRecord = new MedicalRecord();
    medicalRecord.setBirthdate("01/01/2000");
    MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO();
    when(medicalRecordRepository.save(any(MedicalRecord.class))).thenReturn(medicalRecord);

    // WHEN
    MedicalRecord created = medicalRecordService.addMedicalRecord(medicalRecordDTO);

    // THEN
    assertEquals(created.getBirthdate(), medicalRecord.getBirthdate());
    verify(medicalRecordRepository).save(any(MedicalRecord.class));
  }

  @Test
  public void addMedicalRecordTest_shouldReturnAlreadyExist () {
    // GIVEN
    MedicalRecord medicalRecord = new MedicalRecord();
    medicalRecord.setId(1L);
    MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO();
    medicalRecordDTO.setId(medicalRecord.getId());

    // WHEN
    when(medicalRecordRepository.existsById(anyLong())).thenReturn(TRUE);

    // THEN
    Throwable exception = assertThrows(EntityExistsException.class, () -> medicalRecordService.addMedicalRecord(medicalRecordDTO));
    assertEquals("medicalrecord 1 already exists", exception.getMessage());
  }

  @Test
  public void getMedicalRecords_shouldReturnOk () {
    // GIVEN
    List<MedicalRecord> medicalRecords = new ArrayList();
    medicalRecords.add(new MedicalRecord());
    when(medicalRecordRepository.findAll()).thenReturn(medicalRecords);

    // WHEN
    Iterable<MedicalRecord> expected = medicalRecordService.getMedicalRecords();

    // THEN
    assertEquals(expected, medicalRecords);
    verify(medicalRecordRepository).findAll();
  }

  @Test
  public void deleteMedicalRecord_shouldReturnOk () {
    // GIVEN
    MedicalRecord medicalRecord = new MedicalRecord();
    medicalRecord.setId(1L);
    when(medicalRecordRepository.existsById(anyLong())).thenReturn(TRUE);

    // WHEN
    medicalRecordService.deleteMedicalRecord(medicalRecord.getId());

    // THEN
    verify(medicalRecordRepository).deleteById(medicalRecord.getId());
  }

  @Test
  public void deleteMedicalRecord_shouldReturnNotFound () {
    // GIVEN
    MedicalRecord medicalRecord = new MedicalRecord();
    medicalRecord.setId(1L);

    // WHEN
    when(medicalRecordRepository.existsById(anyLong())).thenReturn(FALSE);

    // THEN
    Throwable exception = assertThrows(NoSuchElementException.class, () -> medicalRecordService.deleteMedicalRecord(medicalRecord.getId()));
    assertEquals("medicalrecord 1 doesn't exist", exception.getMessage());
  }

  @Test
  public void updateMedicalRecord_shouldReturnOk () {
    // GIVEN
    MedicalRecord medicalRecord = new MedicalRecord();
    medicalRecord.setId(1L);
    medicalRecord.setBirthdate("01/01/2000");
    MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO();
    when(medicalRecordRepository.findById(anyLong())).thenReturn(java.util.Optional.of(medicalRecord));
    when(mapService.updateMedicalRecordWithMedicalRecordDTO(medicalRecord, medicalRecordDTO)).thenReturn(medicalRecord);
    when(medicalRecordRepository.save(any(MedicalRecord.class))).thenReturn(medicalRecord);

    // WHEN
    MedicalRecord updated = medicalRecordService.updateMedicalRecord(medicalRecord.getId(), medicalRecordDTO);

    // THEN
    assertEquals(medicalRecord.getBirthdate(), updated.getBirthdate());
    verify(medicalRecordRepository).findById(medicalRecord.getId());
    verify(medicalRecordRepository).save(medicalRecord);
  }

  @Test
  public void updateMedicalRecord_shouldReturnNotFound () {
    // GIVEN
    MedicalRecord medicalRecord = new MedicalRecord();
    medicalRecord.setId(1L);
    MedicalRecordDTO medicalRecordDTO = new MedicalRecordDTO();

    // THEN
    Throwable exception = assertThrows(EntityNotFoundException.class, () -> medicalRecordService.updateMedicalRecord(medicalRecord.getId(), medicalRecordDTO));
    assertEquals("medicalrecord 1 doesn't exist", exception.getMessage());
  }

  @Test
  public void getMedicalRecord_shouldReturnOk () {
    // GIVEN
    MedicalRecord medicalRecord = new MedicalRecord();
    medicalRecord.setId(1L);
    when(medicalRecordRepository.findById(anyLong())).thenReturn(java.util.Optional.of(medicalRecord));

    // WHEN
    MedicalRecord expected = medicalRecordService.getMedicalRecord(medicalRecord.getId());

    // THEN
    assertEquals(expected, medicalRecord);
    verify(medicalRecordRepository).findById(medicalRecord.getId());
  }

  @Test
  public void getMedicalRecord_shouldReturnNotFound () {
    // GIVEN
    MedicalRecord medicalRecord = new MedicalRecord();
    medicalRecord.setId(1L);

    // WHEN
    when(medicalRecordRepository.findById(anyLong())).thenReturn(null);

    // THEN
    assertThrows(NullPointerException.class, () -> medicalRecordService.getMedicalRecord(medicalRecord.getId()));
  }

}
