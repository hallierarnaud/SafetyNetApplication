package com.openclassrooms.safetynet;

import com.openclassrooms.safetynet.controller.PersonController;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.service.PersonService;

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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PersonController.class)
public class PersonControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PersonService personService;

  @Test
  public void getPersons_shouldReturnOk() throws Exception {
    when(personService.getPerson(any())).thenReturn(new Person());
    mockMvc.perform(get("/persons")).andExpect(status().isOk());
  }

  @Test
  public void getPersonById_shouldReturnOk() throws Exception {
    when(personService.getPerson(any())).thenReturn(new Person());
    mockMvc.perform(get("/persons/1")).andExpect(status().isOk());
  }

  @Test
  public void getPersonById_shouldReturnNotFound() throws Exception {
    when(personService.getPerson(any())).thenThrow(NoSuchElementException.class);
    mockMvc.perform(get("/persons/1")).andExpect(status().isNotFound());
  }

  @Test
  public void postPerson_shouldReturnOk() throws Exception {
    mockMvc.perform(post("/persons")
            .param("id", "1")
            .param("firstName", "Bart")
            .param("lastName", "Simpson")
            .param("address", "unknown")
            .param("city", "SpringVille")
            .param("zip", "11111")
            .param("phone", "555-555")
            .param("email", "bsimpson@email.com"))
            .andExpect(status().isOk());
  }

  @Test
  public void deletePerson_shouldReturnOk() throws Exception {
    when(personService.getPerson(any())).thenReturn(new Person());
    mockMvc.perform(delete("/persons/1")).andExpect(status().isOk());
  }

}
