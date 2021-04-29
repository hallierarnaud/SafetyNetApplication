package com.openclassrooms.safetynet.DAOTest;

import com.openclassrooms.safetynet.domain.object.FireStation;
import com.openclassrooms.safetynet.model.DAO.FireStationDAO;
import com.openclassrooms.safetynet.model.DAO.MapDAO;
import com.openclassrooms.safetynet.model.entity.FireStationEntity;
import com.openclassrooms.safetynet.model.repository.FireStationRepository;

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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FireStationDAOTest {

  @Mock
  private MapDAO mapDAO;

  @Mock
  private FireStationRepository fireStationRepository;

  @InjectMocks
  private FireStationDAO fireStationDAO;

  @Test
  public void findById_shouldReturnOk() {
    // GIVEN
    FireStationEntity fireStationEntity = new FireStationEntity();
    FireStation fireStation = new FireStation();
    when(fireStationRepository.findById(anyLong())).thenReturn(java.util.Optional.of(fireStationEntity));

    // WHEN
    FireStation fireStationExpected = fireStationDAO.findById(anyLong());

    // THEN
    assertEquals(fireStationExpected, fireStation);
    verify(fireStationRepository).findById(anyLong());
  }

  @Test
  public void findById_shouldReturnNotFound() {
    // GIVEN
    FireStation fireStation = new FireStation();
    fireStation.setId(100L);

    // THEN
    assertThrows(NoSuchElementException.class, () -> fireStationDAO.findById(fireStation.getId()));
  }

  @Test
  public void findAll_shouldReturnOk() {
    // GIVEN
    List<FireStationEntity> fireStationEntities = new ArrayList<>();
    fireStationEntities.add(new FireStationEntity());
    List<FireStation> fireStations = new ArrayList<>();
    fireStations.add(new FireStation());
    when(fireStationRepository.findAll()).thenReturn(fireStationEntities);

    // WHEN
    List<FireStation> expected = fireStationDAO.findAll();

    // THEN
    assertEquals(expected, fireStations);
    verify(fireStationRepository).findAll();
  }

  @Test
  public void updateFireStation_shouldReturnOK() {
    // GIVEN
    FireStationEntity fireStationEntity = new FireStationEntity();
    FireStation fireStation = new FireStation();
    fireStation.setId(1L);
    fireStation.setStationNumber("1");
    when(fireStationRepository.findById(anyLong())).thenReturn(java.util.Optional.of(fireStationEntity));
    when(fireStationRepository.save(any(FireStationEntity.class))).thenReturn(fireStationEntity);

    // WHEN
    String expectedStationNumber = fireStationDAO.updateFireStation(1l, fireStation).getStationNumber();

    // THEN
    assertEquals("1", expectedStationNumber);
    verify(fireStationRepository).findById(anyLong());
    verify(fireStationRepository).save(any(FireStationEntity.class));
  }

  @Test
  public void updateFireStation_shouldReturnNotFound() {
    // GIVEN
    FireStation fireStation = new FireStation();
    fireStation.setId(100L);

    // THEN
    Throwable exception = assertThrows(NoSuchElementException.class, () -> fireStationDAO.updateFireStation(fireStation.getId(), fireStation));
    assertEquals("firestation 100 doesn't exist", exception.getMessage());
  }

  @Test
  public void addSFireStation_shouldReturnOK() {
    // GIVEN
    FireStationEntity fireStationEntity = new FireStationEntity();
    FireStation fireStation = new FireStation();
    fireStation.setId(1L);
    fireStation.setStationNumber("1");
    when(fireStationRepository.save(any(FireStationEntity.class))).thenReturn(fireStationEntity);

    // WHEN
    String expectedStationNumber = fireStationDAO.addFireStation(fireStation).getStationNumber();

    // THEN
    assertEquals("1", expectedStationNumber);
    verify(fireStationRepository).save(any(FireStationEntity.class));
  }

  @Test
  public void existById_shouldReturnOk() {
    // GIVEN
    when(fireStationRepository.existsById(anyLong())).thenReturn(true);

    // WHEN
    Boolean existExpected = fireStationDAO.existById(anyLong());

    // THEN
    assertEquals(true, existExpected);
    verify(fireStationRepository).existsById(anyLong());
  }

  @Test
  public void deleteById_shouldReturnOk() {
    // GIVEN
    FireStationEntity fireStationEntity = new FireStationEntity();
    fireStationEntity.setId(1L);

    // WHEN
    fireStationRepository.deleteById(fireStationEntity.getId());

    // THEN
    verify(fireStationRepository).deleteById(anyLong());
  }

}
