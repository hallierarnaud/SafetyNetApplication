package com.openclassrooms.safetynet.controllerTest;

import com.openclassrooms.safetynet.controller.PersonController;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.service.DataReader;
import com.openclassrooms.safetynet.service.FireStationDataImportation;
import com.openclassrooms.safetynet.service.MapService;
import com.openclassrooms.safetynet.service.MedicalRecordDataImportation;
import com.openclassrooms.safetynet.service.PersonDataImportation;
import com.openclassrooms.safetynet.service.PersonService;

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

@WebMvcTest(controllers = PersonController.class)
public class PersonControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PersonService personService;

  @MockBean
  private DataReader dataReader;

  @MockBean
  private PersonDataImportation personDataImportation;

  @MockBean
  private FireStationDataImportation fireStationDataImportation;

  @MockBean
  private MedicalRecordDataImportation medicalRecordDataImportation;

  @MockBean
  private MapService mapService;

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
    when(personService.addPerson(any())).thenReturn(new Person());
    mockMvc.perform(post("/persons")
            .contentType(MediaType.APPLICATION_JSON).content("{}"))
            .andExpect(status().isOk());
  }

  @Test
  public void postPerson_shouldReturnUnprocessableEntity() throws Exception {
    when(personService.addPerson(any())).thenThrow(EntityExistsException.class);
    mockMvc.perform(post("/persons")
            .contentType(MediaType.APPLICATION_JSON).content("{}"))
            .andExpect(status().isUnprocessableEntity());
  }

  @Test
  public void deletePerson_shouldReturnOk() throws Exception {
    doNothing().when(personService).deletePerson(any());
    mockMvc.perform(delete("/persons/1")).andExpect(status().isOk());
  }

  @Test
  public void deletePerson_shouldReturnNotFound() throws Exception {
    doThrow(NoSuchElementException.class).when(personService).deletePerson(any());
    mockMvc.perform(delete("/persons/1")).andExpect(status().isNotFound());
  }

  @Test
  public void updatePerson_shouldReturnOk() throws Exception {
    when(personService.updatePerson(any(), any())).thenReturn(new Person());
    mockMvc.perform(put("/persons/1")
            .contentType(MediaType.APPLICATION_JSON).content("{}"))
            .andExpect(status().isOk());
  }

  @Test
  public void updatePerson_shouldReturnUnprocessableEntity() throws Exception {
    when(personService.updatePerson(any(), any())).thenThrow(EntityNotFoundException.class);
    mockMvc.perform(put("/persons/1")
            .contentType(MediaType.APPLICATION_JSON).content("{}"))
            .andExpect(status().isUnprocessableEntity());
  }

}
