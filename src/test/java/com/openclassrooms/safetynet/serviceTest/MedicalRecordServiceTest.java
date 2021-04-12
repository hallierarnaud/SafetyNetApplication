package com.openclassrooms.safetynet.serviceTest;

import com.openclassrooms.safetynet.domain.service.MapService;
import com.openclassrooms.safetynet.domain.service.MedicalRecordService;
import com.openclassrooms.safetynet.model.repository.MedicalRecordRepository;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MedicalRecordServiceTest {

  @Mock
  private MedicalRecordRepository medicalRecordRepository;

  @Mock
  private MapService mapService;

  @InjectMocks
  private MedicalRecordService medicalRecordService;

  /*@Test
  public void addMedicalRecordTest_shouldReturnOk () {
    // GIVEN
    MedicalRecordEntity medicalRecord = new MedicalRecordEntity();
    medicalRecord.setBirthdate("01/01/2000");
    MedicalRecordResponse medicalRecordResponse = new MedicalRecordResponse();
    when(medicalRecordRepository.save(any(MedicalRecordEntity.class))).thenReturn(medicalRecord);

    // WHEN
    MedicalRecordEntity created = medicalRecordService.addMedicalRecord(medicalRecordResponse);

    // THEN
    assertEquals(created.getBirthdate(), medicalRecord.getBirthdate());
    verify(medicalRecordRepository).save(any(MedicalRecordEntity.class));
  }

  @Test
  public void addMedicalRecordTest_shouldReturnAlreadyExist () {
    // GIVEN
    MedicalRecordEntity medicalRecord = new MedicalRecordEntity();
    medicalRecord.setId(1L);
    MedicalRecordResponse medicalRecordResponse = new MedicalRecordResponse();
    medicalRecordResponse.setId(medicalRecord.getId());

    // WHEN
    when(medicalRecordRepository.existsById(anyLong())).thenReturn(TRUE);

    // THEN
    Throwable exception = assertThrows(EntityExistsException.class, () -> medicalRecordService.addMedicalRecord(medicalRecordResponse));
    assertEquals("medicalrecord 1 already exists", exception.getMessage());
  }

  @Test
  public void getMedicalRecords_shouldReturnOk () {
    // GIVEN
    List<MedicalRecordEntity> medicalRecords = new ArrayList();
    medicalRecords.add(new MedicalRecordEntity());
    when(medicalRecordRepository.findAll()).thenReturn(medicalRecords);

    // WHEN
    Iterable<MedicalRecordEntity> expected = medicalRecordService.getMedicalRecords();

    // THEN
    assertEquals(expected, medicalRecords);
    verify(medicalRecordRepository).findAll();
  }

  @Test
  public void deleteMedicalRecord_shouldReturnOk () {
    // GIVEN
    MedicalRecordEntity medicalRecord = new MedicalRecordEntity();
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
    MedicalRecordEntity medicalRecord = new MedicalRecordEntity();
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
    MedicalRecordEntity medicalRecord = new MedicalRecordEntity();
    medicalRecord.setId(1L);
    medicalRecord.setBirthdate("01/01/2000");
    MedicalRecordResponse medicalRecordResponse = new MedicalRecordResponse();
    when(medicalRecordRepository.findById(anyLong())).thenReturn(java.util.Optional.of(medicalRecord));
    when(mapService.updateMedicalRecordWithMedicalRecordDTO(medicalRecord, medicalRecordResponse)).thenReturn(medicalRecord);
    when(medicalRecordRepository.save(any(MedicalRecordEntity.class))).thenReturn(medicalRecord);

    // WHEN
    MedicalRecordEntity updated = medicalRecordService.updateMedicalRecord(medicalRecord.getId(), medicalRecordResponse);

    // THEN
    assertEquals(medicalRecord.getBirthdate(), updated.getBirthdate());
    verify(medicalRecordRepository).findById(medicalRecord.getId());
    verify(medicalRecordRepository).save(medicalRecord);
  }

  @Test
  public void updateMedicalRecord_shouldReturnNotFound () {
    // GIVEN
    MedicalRecordEntity medicalRecord = new MedicalRecordEntity();
    medicalRecord.setId(1L);
    MedicalRecordResponse medicalRecordResponse = new MedicalRecordResponse();

    // THEN
    Throwable exception = assertThrows(EntityNotFoundException.class, () -> medicalRecordService.updateMedicalRecord(medicalRecord.getId(), medicalRecordResponse));
    assertEquals("medicalrecord 1 doesn't exist", exception.getMessage());
  }

  @Test
  public void getMedicalRecord_shouldReturnOk () {
    // GIVEN
    MedicalRecordEntity medicalRecord = new MedicalRecordEntity();
    medicalRecord.setId(1L);
    when(medicalRecordRepository.findById(anyLong())).thenReturn(java.util.Optional.of(medicalRecord));

    // WHEN
    MedicalRecordEntity expected = medicalRecordService.getMedicalRecord(medicalRecord.getId());

    // THEN
    assertEquals(expected, medicalRecord);
    verify(medicalRecordRepository).findById(medicalRecord.getId());
  }

  @Test
  public void getMedicalRecord_shouldReturnNotFound () {
    // GIVEN
    MedicalRecordEntity medicalRecord = new MedicalRecordEntity();
    medicalRecord.setId(1L);

    // WHEN
    when(medicalRecordRepository.findById(anyLong())).thenReturn(null);

    // THEN
    assertThrows(NullPointerException.class, () -> medicalRecordService.getMedicalRecord(medicalRecord.getId()));
  }*/

}
