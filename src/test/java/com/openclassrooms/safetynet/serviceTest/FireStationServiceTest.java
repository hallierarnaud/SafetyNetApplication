package com.openclassrooms.safetynet.serviceTest;

import com.openclassrooms.safetynet.model.entity.FireStationEntity;
import com.openclassrooms.safetynet.model.repository.FireStationRepository;
import com.openclassrooms.safetynet.domain.service.FireStationService;

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
public class FireStationServiceTest {

  @Mock
  private FireStationRepository fireStationRepository;

  @InjectMocks
  private FireStationService fireStationService;

  @Test
  public void addFireStationTest_shouldReturnOk () {
    // GIVEN
    FireStationEntity fireStation = new FireStationEntity();
    fireStation.setStationNumber("1");
    when(fireStationRepository.save(any(FireStationEntity.class))).thenReturn(fireStation);

    // WHEN
    FireStationEntity created = fireStationService.addFireStation(fireStation);

    // THEN
    assertEquals(created.getStationNumber(), fireStation.getStationNumber());
    verify(fireStationRepository).save(fireStation);
  }

  @Test
  public void addFireStationTest_shouldReturnAlreadyExist () {
    // GIVEN
    FireStationEntity fireStation = new FireStationEntity();
    fireStation.setId(1L);

    // WHEN
    when(fireStationRepository.existsById(anyLong())).thenReturn(TRUE);

    // THEN
    Throwable exception = assertThrows(EntityExistsException.class, () -> fireStationService.addFireStation(fireStation));
    assertEquals("firestation 1 already exists", exception.getMessage());
  }

  @Test
  public void getFireStations_shouldReturnOk () {
    // GIVEN
    List<FireStationEntity> fireStations = new ArrayList();
    fireStations.add(new FireStationEntity());
    when(fireStationRepository.findAll()).thenReturn(fireStations);

    // WHEN
    Iterable<FireStationEntity> expected = fireStationService.getFireStations();

    // THEN
    assertEquals(expected, fireStations);
    verify(fireStationRepository).findAll();
  }

  @Test
  public void deleteFireStation_shouldReturnOk () {
    // GIVEN
    FireStationEntity fireStation = new FireStationEntity();
    fireStation.setId(1L);
    when(fireStationRepository.existsById(anyLong())).thenReturn(TRUE);

    // WHEN
    fireStationService.deleteFireStation(fireStation.getId());

    // THEN
    verify(fireStationRepository).deleteById(fireStation.getId());
  }

  @Test
  public void deleteFireStation_shouldReturnNotFound () {
    // GIVEN
    FireStationEntity fireStation = new FireStationEntity();
    fireStation.setId(1L);

    // WHEN
    when(fireStationRepository.existsById(anyLong())).thenReturn(FALSE);

    // THEN
    Throwable exception = assertThrows(NoSuchElementException.class, () -> fireStationService.deleteFireStation(fireStation.getId()));
    assertEquals("firestation 1 doesn't exist", exception.getMessage());
  }

  @Test
  public void updateFireStation_shouldReturnOk () {
    // GIVEN
    FireStationEntity fireStation = new FireStationEntity();
    fireStation.setId(1L);
    fireStation.setStationNumber("1");
    when(fireStationRepository.existsById(anyLong())).thenReturn(TRUE);
    when(fireStationRepository.save(any(FireStationEntity.class))).thenReturn(fireStation);

    // WHEN
    FireStationEntity updated = fireStationService.updateFireStation(fireStation.getId(), fireStation);

    // THEN
    assertEquals(fireStation.getStationNumber(), updated.getStationNumber());
    verify(fireStationRepository).existsById(fireStation.getId());
    verify(fireStationRepository).save(fireStation);
  }

  @Test
  public void updateFireStation_shouldReturnNotFound () {
    // GIVEN
    FireStationEntity fireStation = new FireStationEntity();
    fireStation.setId(1L);

    // WHEN
    when(fireStationRepository.existsById(anyLong())).thenReturn(FALSE);

    // THEN
    Throwable exception = assertThrows(EntityNotFoundException.class, () -> fireStationService.updateFireStation(fireStation.getId(), fireStation));
    assertEquals("firestation 1 doesn't exist", exception.getMessage());
  }

  @Test
  public void getFireStation_shouldReturnOk () {
    // GIVEN
    FireStationEntity fireStation = new FireStationEntity();
    fireStation.setId(1L);
    when(fireStationRepository.findById(anyLong())).thenReturn(java.util.Optional.of(fireStation));

    // WHEN
    FireStationEntity expected = fireStationService.getFireStation(fireStation.getId());

    // THEN
    assertEquals(expected, fireStation);
    verify(fireStationRepository).findById(fireStation.getId());
  }

  @Test
  public void getFireStation_shouldReturnNotFound () {
    // GIVEN
    FireStationEntity fireStation = new FireStationEntity();
    fireStation.setId(1L);

    // WHEN
    when(fireStationRepository.findById(anyLong())).thenReturn(null);

    // THEN
    assertThrows(NullPointerException.class, () -> fireStationService.getFireStation(fireStation.getId()));
  }

}
