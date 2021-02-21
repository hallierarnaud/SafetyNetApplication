package com.openclassrooms.safetynet.IntegrationTest;

import com.openclassrooms.safetynet.model.Person;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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
            .content("Homer"))
            .andExpect(status().isOk());
  }

  @Test
  public void testAddPerson_shouldReturnUnprocessableEntity() throws Exception {
    mockMvc.perform(post("/persons")
            .contentType(MediaType.APPLICATION_JSON).content("{}"))
            .andExpect(status().isUnprocessableEntity());
  }

  @Test
  public void testDeletePerson_shouldReturnOk() throws Exception {
    mockMvc.perform(delete("/persons/1")).andExpect(status().isOk());
    mockMvc.perform(get("/persons/1")).andExpect(status().isNotFound());
  }

  @Test
  public void testDeletePerson_shouldReturnNotFound() throws Exception {
    mockMvc.perform(delete("/persons/100")).andExpect(status().isNotFound());
  }

  @Test
  public void testUpdatePerson_shouldReturnOk() throws Exception {
    Person person = new Person();
    person.setFirstName("Homer");
    mockMvc.perform(put("/persons/1")
            .contentType(MediaType.APPLICATION_JSON).content(String.valueOf(person)))
            .andExpect(status().isOk());
    mockMvc.perform(get("/persons/1")).andExpect(status().isOk());
  }

  @Test
  public void testUpdatePerson_shouldReturnNotFound() throws Exception {
    mockMvc.perform(put("/persons/100")
            .contentType(MediaType.APPLICATION_JSON).content("{}"))
            .andExpect(status().isUnprocessableEntity());
  }

}
