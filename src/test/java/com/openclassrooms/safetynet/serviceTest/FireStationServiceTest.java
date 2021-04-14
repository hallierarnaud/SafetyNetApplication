package com.openclassrooms.safetynet.serviceTest;

import com.openclassrooms.safetynet.controller.DTO.FireStationAddOrUpdateRequest;
import com.openclassrooms.safetynet.domain.object.FireStation;
import com.openclassrooms.safetynet.domain.service.FireStationService;
import com.openclassrooms.safetynet.domain.service.MapService;
import com.openclassrooms.safetynet.model.DAO.FireStationDAO;

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
public class FireStationServiceTest {

  @Mock
  private FireStationDAO fireStationDAO;

  @Mock
  private MapService mapService;

  @InjectMocks
  private FireStationService fireStationService;

  @Test
  public void addFireStationTest_shouldReturnOk () {
    // GIVEN
    FireStationAddOrUpdateRequest fireStationAddRequest = new FireStationAddOrUpdateRequest();
    fireStationAddRequest.setStationNumber("1");
    FireStation fireStation = new FireStation();
    when(fireStationDAO.addFireStation(any(FireStation.class))).thenReturn(fireStation);

    // WHEN
    FireStation created = fireStationService.addFireStation(fireStationAddRequest);

    // THEN
    assertEquals(created.getStationNumber(), fireStation.getStationNumber());
    verify(fireStationDAO).addFireStation(fireStation);
  }

  @Test
  public void addFireStationTest_shouldReturnAlreadyExist () {
    // GIVEN
    FireStationAddOrUpdateRequest fireStationAddRequest = new FireStationAddOrUpdateRequest();
    fireStationAddRequest.setId(1L);

    // WHEN
    when(fireStationDAO.existById(anyLong())).thenReturn(TRUE);

    // THEN
    Throwable exception = assertThrows(EntityExistsException.class, () -> fireStationService.addFireStation(fireStationAddRequest));
    assertEquals("firestation 1 already exists", exception.getMessage());
  }

  @Test
  public void getFireStations_shouldReturnOk () {
    // GIVEN
    List<FireStation> fireStations = new ArrayList();
    fireStations.add(new FireStation());
    when(fireStationDAO.findAll()).thenReturn(fireStations);

    // WHEN
    List<FireStation> expected = fireStationService.getFireStations();

    // THEN
    assertEquals(expected, fireStations);
    verify(fireStationDAO).findAll();
  }

  @Test
  public void deleteFireStation_shouldReturnOk () {
    // GIVEN
    FireStation fireStation = new FireStation();
    fireStation.setId(1L);
    when(fireStationDAO.existById(anyLong())).thenReturn(TRUE);

    // WHEN
    fireStationService.deleteFireStation(fireStation.getId());

    // THEN
    verify(fireStationDAO).deleteById(fireStation.getId());
  }

  @Test
  public void deleteFireStation_shouldReturnNotFound () {
    // GIVEN
    FireStation fireStation = new FireStation();
    fireStation.setId(1L);

    // WHEN
    when(fireStationDAO.existById(anyLong())).thenReturn(FALSE);

    // THEN
    Throwable exception = assertThrows(NoSuchElementException.class, () -> fireStationService.deleteFireStation(fireStation.getId()));
    assertEquals("firestation 1 doesn't exist", exception.getMessage());
  }

  @Test
  public void updateFireStation_shouldReturnOk () {
    // GIVEN
    FireStationAddOrUpdateRequest fireStationUpdateRequest = new FireStationAddOrUpdateRequest();
    fireStationUpdateRequest.setStationNumber("1");
    FireStation fireStation = new FireStation();
    fireStation.setId(1L);
    when(fireStationDAO.findById(anyLong())).thenReturn(fireStation);
    when(fireStationDAO.updateFireStation(anyLong(), any(FireStation.class))).thenReturn(fireStation);

    // WHEN
    FireStation updated = fireStationService.updateFireStation(fireStation.getId(), fireStationUpdateRequest);

    // THEN
    assertEquals(fireStation.getStationNumber(), updated.getStationNumber());
    verify(fireStationDAO, times(2)).findById(fireStation.getId());
    verify(fireStationDAO).updateFireStation(fireStation.getId(), fireStation);
  }

  @Test
  public void updateFireStation_shouldReturnNotFound () {
    // GIVEN
    FireStation fireStation = new FireStation();
    fireStation.setId(100L);
    FireStationAddOrUpdateRequest fireStationUpdateRequest = new FireStationAddOrUpdateRequest();

    // THEN
    Throwable exception = assertThrows(NoSuchElementException.class, () -> fireStationService.updateFireStation(fireStation.getId(), fireStationUpdateRequest));
    assertEquals("firestation 100 doesn't exist", exception.getMessage());
  }

  @Test
  public void getFireStation_shouldReturnOk () {
    // GIVEN
    FireStation fireStation = new FireStation();
    fireStation.setId(1L);
    when(fireStationDAO.findById(anyLong())).thenReturn(fireStation);

    // WHEN
    FireStation expected = fireStationService.getFireStation(fireStation.getId());

    // THEN
    assertEquals(expected, fireStation);
    verify(fireStationDAO, times(2)).findById(fireStation.getId());
  }

  @Test
  public void getFireStation_shouldReturnNotFound () {
    // GIVEN
    FireStation fireStation = new FireStation();
    fireStation.setId(100L);

    // WHEN
    when(fireStationDAO.findById(anyLong())).thenReturn(null);

    // THEN
    assertThrows(NoSuchElementException.class, () -> fireStationService.getFireStation(fireStation.getId()));
  }

}
