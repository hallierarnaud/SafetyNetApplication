package com.openclassrooms.safetynet.controllerTest;

import com.openclassrooms.safetynet.controller.endpoint.FireStationController;
import com.openclassrooms.safetynet.model.entity.FireStationEntity;
import com.openclassrooms.safetynet.domain.service.DataReader;
import com.openclassrooms.safetynet.domain.service.FireStationDataImportation;
import com.openclassrooms.safetynet.domain.service.FireStationService;
import com.openclassrooms.safetynet.domain.service.MedicalRecordDataImportation;
import com.openclassrooms.safetynet.domain.service.PersonDataImportation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.NoSuchElementException;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FireStationController.class)
public class FireStationControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private FireStationService fireStationService;

  @MockBean
  private DataReader dataReader;

  @MockBean
  private PersonDataImportation personDataImportation;

  @MockBean
  private FireStationDataImportation fireStationDataImportation;

  @MockBean
  private MedicalRecordDataImportation medicalRecordDataImportation;

  @Test
  public void getFireStations_shouldReturnOk() throws Exception {
    when(fireStationService.getFireStation(any())).thenReturn(new FireStationEntity());
    mockMvc.perform(get("/firestations")).andExpect(status().isOk());
  }

  @Test
  public void getFireStationById_shouldReturnOk() throws Exception {
    when(fireStationService.getFireStation(any())).thenReturn(new FireStationEntity());
    mockMvc.perform(get("/firestations/1")).andExpect(status().isOk());
  }

  @Test
  public void getFireStationById_shouldReturnNotFound() throws Exception {
    when(fireStationService.getFireStation(any())).thenThrow(NoSuchElementException.class);
    mockMvc.perform(get("/firestations/1")).andExpect(status().isNotFound());
  }

  @Test
  public void postFireStation_shouldReturnOk() throws Exception {
    when(fireStationService.addFireStation(any())).thenReturn(new FireStationEntity());
    mockMvc.perform(post("/firestations")
            .contentType(MediaType.APPLICATION_JSON).content("{}"))
            .andExpect(status().isOk());
  }

  @Test
  public void postFireStation_shouldReturnUnprocessableEntity() throws Exception {
    when(fireStationService.addFireStation(any())).thenThrow(EntityExistsException.class);
    mockMvc.perform(post("/firestations")
            .contentType(MediaType.APPLICATION_JSON).content("{}"))
            .andExpect(status().isUnprocessableEntity());
  }

  @Test
  public void deleteFireStation_shouldReturnOk() throws Exception {
    doNothing().when(fireStationService).deleteFireStation(any());
    mockMvc.perform(delete("/firestations/1")).andExpect(status().isOk());
  }

  @Test
  public void deleteFireStation_shouldReturnNotFound() throws Exception {
    doThrow(NoSuchElementException.class).when(fireStationService).deleteFireStation(any());
    mockMvc.perform(delete("/firestations/1")).andExpect(status().isNotFound());
  }

  @Test
  public void updateFireStation_shouldReturnOk() throws Exception {
    when(fireStationService.updateFireStation(any(), any())).thenReturn(new FireStationEntity());
    mockMvc.perform(put("/firestations/1")
            .contentType(MediaType.APPLICATION_JSON).content("{}"))
            .andExpect(status().isOk());
  }

  @Test
  public void updateFireStation_shouldReturnUnprocessableEntity() throws Exception {
    when(fireStationService.updateFireStation(any(), any())).thenThrow(EntityNotFoundException.class);
    mockMvc.perform(put("/firestations/1")
            .contentType(MediaType.APPLICATION_JSON).content("{}"))
            .andExpect(status().isUnprocessableEntity());
  }

}
