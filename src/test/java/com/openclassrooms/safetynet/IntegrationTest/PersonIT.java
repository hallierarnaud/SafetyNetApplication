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
public class PersonIT {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void testGetPersons_shouldReturnOk() throws Exception {
    mockMvc.perform(get("/persons"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].firstName", is("John")));
  }

  @Test
  public void testGetPerson_shouldReturnOk() throws Exception {
    mockMvc.perform(get("/persons/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.firstName", is("John")));
  }

  @Test
  public void testGetPerson_shouldReturnNotFound() throws Exception {
    mockMvc.perform(get("/persons/100"))
            .andExpect(status().isNotFound());
  }

  @Test
  public void testAddPerson_shouldReturnOk() throws Exception {
    mockMvc.perform(post("/persons")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"id\": \"100\", \"firstName\":\"Bart\",\"lastName\":\"Simpson\",\"address\":\"1509 Culver St\",\"city\":\"Springville\",\"zip\":\"97451\",\"phone\":\"555-555\",\"email\":\"simpson@email.com\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.firstName", is("Bart")));
  }

  @Test
  public void testAddPerson_shouldReturnUnprocessableEntity() throws Exception {
    mockMvc.perform(post("/persons")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"id\": \"1\", \"firstName\":\"Bart\",\"lastName\":\"Simpson\",\"address\":\"1509 Culver St\",\"city\":\"Springville\",\"zip\":\"97451\",\"phone\":\"555-555\",\"email\":\"simpson@email.com\"}"))
            .andExpect(status().isUnprocessableEntity());
  }

  @Test
  public void testDeletePerson_shouldReturnOk() throws Exception {
    mockMvc.perform(delete("/persons/John/Boyd"));
    mockMvc.perform(get("/persons/1"))
            .andExpect(status().isNotFound());
  }

  @Test
  public void testDeletePerson_shouldReturnNotFound() throws Exception {
    mockMvc.perform(delete("/persons/Clark/Kent"))
            .andExpect(status().isNotFound());
  }

  @Test
  public void testUpdatePerson_shouldReturnOk() throws Exception {
    mockMvc.perform(get("/persons/1"))
            .andExpect(jsonPath("$.city", is("Culver")));
    mockMvc.perform(put("/persons/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"id\": \"1\", \"firstName\":\"Bart\",\"lastName\":\"Simpson\",\"address\":\"1509 Culver St\",\"city\":\"Springville\",\"zip\":\"97451\",\"phone\":\"555-555\",\"email\":\"simpson@email.com\"}"))
            .andExpect(status().isOk());
    mockMvc.perform(get("/persons/1"))
            .andExpect(jsonPath("$.city", is("Springville")));
  }

  @Test
  public void testUpdatePerson_shouldReturnNotFound() throws Exception {
    mockMvc.perform(put("/persons/100")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{}"))
            .andExpect(status().isUnprocessableEntity());
  }

  @Test
  public void testGetPersonsByFireStation_shouldReturnOk() throws Exception {
    mockMvc.perform(get("/firestation")
            .contentType(MediaType.APPLICATION_JSON)
            .param("stationNumber", "1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.personsByFireStation.[0]firstName", is("Peter")));
  }

  @Test
  public void testGetChildrenByAddress_shouldReturnOk() throws Exception {
    mockMvc.perform(get("/childAlert")
            .contentType(MediaType.APPLICATION_JSON)
            .param("address", "892 Downing Ct"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.childrenByAddressList.[0]firstName", is("Zach")));
  }

  @Test
  public void testGetPhonesByFireStation_shouldReturnOk() throws Exception {
    mockMvc.perform(get("/phoneAlert")
            .contentType(MediaType.APPLICATION_JSON)
            .param("stationNumber", "2"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].phone", is("841-874-6513")));
  }

  @Test
  public void testGetPersonByAddress_shouldReturnOk() throws Exception {
    mockMvc.perform(get("/fire")
            .contentType(MediaType.APPLICATION_JSON)
            .param("address", "892 Downing Ct"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.namePhoneAgeAndMedicalRecordResponseList.[0]lastName", is("Zemicks")));
  }

  @Test
  public void testGetPersonInfo_shouldReturnOk() throws Exception {
    mockMvc.perform(get("/personInfo")
            .contentType(MediaType.APPLICATION_JSON)
            .param("lastName", "Boyd"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].lastName", is("Boyd")));
  }

  @Test
  public void testGetEmailsByCity_shouldReturnOk() throws Exception {
    mockMvc.perform(get("/communityEmail")
            .contentType(MediaType.APPLICATION_JSON)
            .param("city", "Culver"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].email", is("drk@email.com")));
  }

}
