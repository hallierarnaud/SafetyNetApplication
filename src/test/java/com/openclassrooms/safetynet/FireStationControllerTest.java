package com.openclassrooms.safetynet;

import com.openclassrooms.safetynet.controller.FireStationController;
import com.openclassrooms.safetynet.model.FireStation;
import com.openclassrooms.safetynet.service.FireStationService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
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

  @Test
  public void getFireStations_shouldReturnOk() throws Exception {
    when(fireStationService.getFireStation(any())).thenReturn(new FireStation());
    mockMvc.perform(get("/firestations")).andExpect(status().isOk());
  }

  @Test
  public void getFireStationById_shouldReturnOk() throws Exception {
    when(fireStationService.getFireStation(any())).thenReturn(new FireStation());
    mockMvc.perform(get("/firestations/1")).andExpect(status().isOk());
  }

  @Test
  public void getFireStationById_shouldReturnNotFound() throws Exception {
    when(fireStationService.getFireStation(any())).thenThrow(NoSuchElementException.class);
    mockMvc.perform(get("/firestations/1")).andExpect(status().isNotFound());
  }

  @Test
  public void postFireStation_shouldReturnOk() throws Exception {
    mockMvc.perform(post("/firestations")
            .param("id", "1")
            .param("address", "unknown")
            .param("station", "1"))
            .andExpect(status().isOk());
  }

  @Test
  public void deleteFireStation_shouldReturnOk() throws Exception {
    when(fireStationService.getFireStation(any())).thenReturn(new FireStation());
    mockMvc.perform(delete("/firestations/1")).andExpect(status().isOk());
  }

  @Test
  public void updateFireStation_shouldReturnOk() throws Exception {
    when(fireStationService.getFireStation(any())).thenReturn(new FireStation());
    mockMvc.perform(put("/persons/1")
            .param("id", "1")
            .param("address", "unknown")
            .param("station", "1"))
            .andExpect(status().isOk());
  }

}
