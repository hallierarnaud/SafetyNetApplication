package com.openclassrooms.safetynet.DAOTest;

import com.openclassrooms.safetynet.domain.object.MedicalRecord;
import com.openclassrooms.safetynet.model.DAO.MapDAO;
import com.openclassrooms.safetynet.model.DAO.MedicalRecordDAO;
import com.openclassrooms.safetynet.model.entity.MedicalRecordEntity;
import com.openclassrooms.safetynet.model.entity.PersonEntity;
import com.openclassrooms.safetynet.model.repository.MedicalRecordRepository;
import com.openclassrooms.safetynet.model.repository.PersonRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordDAOTest {

  @Mock
  private MapDAO mapDAO;

  @Mock
  private PersonRepository personRepository;

  @Mock
  private MedicalRecordRepository medicalRecordRepository;

  @InjectMocks
  private MedicalRecordDAO medicalRecordDAO;

  @Test
  public void findById_shouldReturnOk() {
    // GIVEN
    MedicalRecordEntity medicalRecordEntity = new MedicalRecordEntity();
    MedicalRecord medicalRecord = new MedicalRecord();
    when(medicalRecordRepository.findById(anyLong())).thenReturn(java.util.Optional.of(medicalRecordEntity));

    // WHEN
    MedicalRecord medicalRecordExpected = medicalRecordDAO.findById(anyLong());

    // THEN
    assertEquals(medicalRecordExpected, medicalRecord);
    verify(medicalRecordRepository).findById(anyLong());
  }

  @Test
  public void findById_shouldReturnNotFound() {
    // GIVEN
    MedicalRecord medicalRecord = new MedicalRecord();
    medicalRecord.setId(100L);

    // THEN
    assertThrows(NoSuchElementException.class, () -> medicalRecordDAO.findById(medicalRecord.getId()));
  }

  @Test
  public void findAll_shouldReturnOk() {
    // GIVEN
    List<MedicalRecordEntity> medicalRecordEntities = new ArrayList<>();
    medicalRecordEntities.add(new MedicalRecordEntity());
    List<MedicalRecord> medicalRecords = new ArrayList<>();
    medicalRecords.add(new MedicalRecord());
    when(medicalRecordRepository.findAll()).thenReturn(medicalRecordEntities);

    // WHEN
    List<MedicalRecord> expected = medicalRecordDAO.findAll();

    // THEN
    assertEquals(expected, medicalRecords);
    verify(medicalRecordRepository).findAll();
  }

  @Test
  public void updateMedicalRecord_shouldReturnOK() {
    // GIVEN
    MedicalRecordEntity medicalRecordEntity = new MedicalRecordEntity();
    PersonEntity personEntity = new PersonEntity();
    medicalRecordEntity.setPersonEntity(personEntity);
    MedicalRecord medicalRecord = new MedicalRecord();
    medicalRecord.setId(1L);
    medicalRecord.setBirthdate("01/01/2000");
    when(medicalRecordRepository.findById(anyLong())).thenReturn(java.util.Optional.of(medicalRecordEntity));
    when(medicalRecordRepository.save(any(MedicalRecordEntity.class))).thenReturn(medicalRecordEntity);

    // WHEN
    String expectedBirthDate = medicalRecordDAO.updateMedicalRecord(1l, medicalRecord).getBirthdate();

    // THEN
    assertEquals("01/01/2000", expectedBirthDate);
    verify(medicalRecordRepository).findById(anyLong());
    verify(medicalRecordRepository).save(any(MedicalRecordEntity.class));
  }

  @Test
  public void updateSimplePerson_shouldReturnNotFound() {
    // GIVEN
    MedicalRecord medicalRecord = new MedicalRecord();
    medicalRecord.setId(100L);

    // THEN
    Throwable exception = assertThrows(NoSuchElementException.class, () -> medicalRecordDAO.updateMedicalRecord(medicalRecord.getId(), medicalRecord));
    assertEquals("medicalrecord 100 doesn't exist", exception.getMessage());
  }

  @Test
  public void addSimplePerson_shouldReturnOK() {
    // GIVEN
    MedicalRecordEntity medicalRecordEntity = new MedicalRecordEntity();
    PersonEntity personEntity = new PersonEntity();
    medicalRecordEntity.setPersonEntity(personEntity);
    MedicalRecord medicalRecord = new MedicalRecord();
    medicalRecord.setId(1L);
    medicalRecord.setBirthdate("01/01/2000");
    when(personRepository.findById(anyLong())).thenReturn(java.util.Optional.of(personEntity));
    when(medicalRecordRepository.save(any(MedicalRecordEntity.class))).thenReturn(medicalRecordEntity);

    // WHEN
    String expectedBirthDate = medicalRecordDAO.addMedicalRecord(medicalRecord).getBirthdate();

    // THEN
    assertEquals("01/01/2000", expectedBirthDate);
    verify(personRepository).findById(anyLong());
    verify(medicalRecordRepository).save(any(MedicalRecordEntity.class));
  }

  @Test
  public void existById_shouldReturnOk() {
    // GIVEN
    when(medicalRecordRepository.existsById(anyLong())).thenReturn(true);

    // WHEN
    Boolean existExpected = medicalRecordDAO.existById(anyLong());

    // THEN
    assertEquals(true, existExpected);
    verify(medicalRecordRepository).existsById(anyLong());
  }

  @Test
  public void existByFirstNameAndLastName_shouldReturnOk() {
    // GIVEN
    when(medicalRecordRepository.existsByPersonEntity_FirstNameAndPersonEntity_LastName(anyString(), anyString())).thenReturn(true);

    // WHEN
    Boolean existExpected = medicalRecordDAO.existByFirstAndLastName(anyString(), anyString());

    // THEN
    assertEquals(true, existExpected);
    verify(medicalRecordRepository).existsByPersonEntity_FirstNameAndPersonEntity_LastName(anyString(), anyString());
  }

  @Test
  public void deleteByFirstNameAndLastName_shouldReturnOk() {
    // GIVEN
    MedicalRecordEntity medicalRecordEntity = new MedicalRecordEntity();
    PersonEntity personEntity = new PersonEntity();
    personEntity.setFirstName("Homer");
    personEntity.setLastName("Simpson");
    medicalRecordEntity.setPersonEntity(personEntity);

    // WHEN
    medicalRecordRepository.deleteByPersonEntity_FirstNameAndPersonEntity_LastName(personEntity.getFirstName(), personEntity.getLastName());

    // THEN
    verify(medicalRecordRepository).deleteByPersonEntity_FirstNameAndPersonEntity_LastName(anyString(), anyString());
  }

}
