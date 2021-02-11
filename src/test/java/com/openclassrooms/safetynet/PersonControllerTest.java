package com.openclassrooms.safetynet;

import com.openclassrooms.safetynet.controller.PersonController;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.service.PersonService;

import org.hibernate.collection.internal.PersistentBag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Iterator;
import java.util.NoSuchElementException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

}
