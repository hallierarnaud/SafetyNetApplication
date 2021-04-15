package com.openclassrooms.safetynet.IntegrationTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FireStationIT {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void testGetFireStations_shouldReturnOk() throws Exception {
    mockMvc.perform(get("/firestations"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].stationNumber", is("2")));

  }

  @Test
  public void testGetFireStation_shouldReturnOk() throws Exception {
    mockMvc.perform(get("/firestations/2"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.stationNumber", is("3")));
  }

  @Test
  public void testGetFireStation_shouldReturnNotFound() throws Exception {
    mockMvc.perform(get("/firestations/100"))
            .andExpect(status().isNotFound());
  }

  @Test
  public void testAddFireStation_shouldReturnOk() throws Exception {
    mockMvc.perform(post("/firestations")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"id\": \"100\", \"addresses\":[\"1509 Culver St\"], \"stationNumber\":\"3\" }"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.stationNumber", is("3")));
  }

  @Test
  public void testAddFireStation_shouldReturnUnprocessableEntity() throws Exception {
    mockMvc.perform(post("/firestations")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"id\": \"2\", \"addresses\":[\"1509 Culver St\"], \"stationNumber\":\"3\" }"))
            .andExpect(status().isUnprocessableEntity());
  }

  @Test
  public void testDeleteFireStation_shouldReturnOk() throws Exception {
    mockMvc.perform(delete("/firestations/1"));
    mockMvc.perform(get("/firestations/1"))
            .andExpect(status().isNotFound());
  }

  @Test
  public void testDeleteFireStation_shouldReturnNotFound() throws Exception {
    mockMvc.perform(delete("/firestations/100"))
            .andExpect(status().isNotFound());
  }

  @Test
  public void testUpdateFireStation_shouldReturnOk() throws Exception {
    mockMvc.perform(get("/firestations/2"))
            .andExpect(jsonPath("$.stationNumber", is("2")));
    mockMvc.perform(put("/firestations/2")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"id\": \"1\", \"addresses\":[\"1509 Culver St\"], \"stationNumber\":\"3\" }"))
            .andExpect(status().isOk());
    mockMvc.perform(get("/firestations/2"))
            .andExpect(jsonPath("$.stationNumber", is("3")));
  }

  @Test
  public void testUpdateFireStation_shouldReturnNotFound() throws Exception {
    mockMvc.perform(put("/firestations/100")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{}"))
            .andExpect(status().isUnprocessableEntity());
  }

}
