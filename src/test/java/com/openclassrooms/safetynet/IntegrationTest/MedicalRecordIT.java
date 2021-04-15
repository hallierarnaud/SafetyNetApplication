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
public class MedicalRecordIT {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void testGetMedicalRecords_shouldReturnOk() throws Exception {
    mockMvc.perform(get("/medicalrecords"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].birthdate", is("03/06/1984")));
  }

  @Test
  public void testGetMedicalRecord_shouldReturnOk() throws Exception {
    mockMvc.perform(get("/medicalrecords/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.birthdate", is("03/06/1984")));
  }

  @Test
  public void testGetMedicalRecord_shouldReturnNotFound() throws Exception {
    mockMvc.perform(get("/medicalrecords/100"))
            .andExpect(status().isNotFound());
  }

  @Test
  public void testAddMedicalRecord_shouldReturnOk() throws Exception {
    mockMvc.perform(post("/persons")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"id\": \"24\", \"firstName\":\"Bart\",\"lastName\":\"Simpson\",\"address\":\"1509 Culver St\",\"city\":\"Springville\",\"zip\":\"97451\",\"phone\":\"555-555\",\"email\":\"simpson@email.com\"}"));
    mockMvc.perform(post("/medicalrecords")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"id\": \"24\", \"birthdate\":\"03/06/1984\", \"medications\":[\"aznol:350mg\", \"hydrapermazol:100mg\"], \"allergies\":[\"nillacilan\"] }"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.birthdate", is("03/06/1984")));
  }

  public void testAddMedicalRecord_shouldReturnUnprocessableEntity() throws Exception {
    mockMvc.perform(post("/medicalrecords")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"id\": \"1\", \"birthdate\":\"03/06/1984\", \"medications\":[\"aznol:350mg\", \"hydrapermazol:100mg\"], \"allergies\":[\"nillacilan\"] }"))
            .andExpect(status().isUnprocessableEntity());
  }

  @Test
  public void testDeleteMedicalRecord_shouldReturnOk() throws Exception {
    mockMvc.perform(delete("/medicalrecords/John/Boyd"));
    mockMvc.perform(get("/medicalrecords/1"))
            .andExpect(status().isNotFound());
  }

  @Test
  public void testDeleteMedicalRecord_shouldReturnNotFound() throws Exception {
    mockMvc.perform(delete("/medicalrecords/Clark/Kent"))
            .andExpect(status().isNotFound());
  }

  @Test
  public void testUpdateMedicalRecord_shouldReturnOk() throws Exception {
    mockMvc.perform(get("/medicalrecords/1"))
            .andExpect(jsonPath("$.birthdate", is("03/06/1984")));
    mockMvc.perform(put("/medicalrecords/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"birthdate\":\"01/01/2000\", \"medications\":[\"aznol:350mg\", \"hydrapermazol:100mg\"], \"allergies\":[\"nillacilan\"] }"))
            .andExpect(status().isOk());
    mockMvc.perform(get("/medicalrecords/1"))
            .andExpect(jsonPath("$.birthdate", is("01/01/2000")));
  }

  @Test
  public void testUpdateMedicalRecord_shouldReturnNotFound() throws Exception {
    mockMvc.perform(put("/medicalrecords/100")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{}"))
            .andExpect(status().isUnprocessableEntity());
  }

}
