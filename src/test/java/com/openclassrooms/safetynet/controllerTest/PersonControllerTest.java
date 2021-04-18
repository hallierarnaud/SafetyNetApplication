package com.openclassrooms.safetynet.controllerTest;

import com.openclassrooms.safetynet.controller.DTO.ChildrenByAddressResponse;
import com.openclassrooms.safetynet.controller.DTO.PersonByAddressResponse;
import com.openclassrooms.safetynet.controller.DTO.PersonByFireStationResponse;
import com.openclassrooms.safetynet.controller.endpoint.PersonController;
import com.openclassrooms.safetynet.domain.object.Person;
import com.openclassrooms.safetynet.domain.service.DataImportation.DataReader;
import com.openclassrooms.safetynet.domain.service.DataImportation.FireStationDataImportation;
import com.openclassrooms.safetynet.domain.service.DataImportation.MedicalRecordDataImportation;
import com.openclassrooms.safetynet.domain.service.DataImportation.PersonDataImportation;
import com.openclassrooms.safetynet.domain.service.MapService;
import com.openclassrooms.safetynet.domain.service.PersonService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import javax.persistence.EntityExistsException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
    when(personService.addSimplePerson(any())).thenReturn(new Person());
    mockMvc.perform(post("/persons")
            .contentType(MediaType.APPLICATION_JSON).content("{}"))
            .andExpect(status().isOk());
  }

  @Test
  public void postPerson_shouldReturnUnprocessableEntity() throws Exception {
    when(personService.addSimplePerson(any())).thenThrow(EntityExistsException.class);
    mockMvc.perform(post("/persons")
            .contentType(MediaType.APPLICATION_JSON).content("{}"))
            .andExpect(status().isUnprocessableEntity());
  }

  @Test
  public void deletePerson_shouldReturnOk() throws Exception {
    doNothing().when(personService).deletePerson(any(), any());
    mockMvc.perform(delete("/persons/John/Boyd")).andExpect(status().isOk());
  }

  @Test
  public void deletePerson_shouldReturnNotFound() throws Exception {
    doThrow(NoSuchElementException.class).when(personService).deletePerson(any(), any());
    mockMvc.perform(delete("/persons/John/Boyd")).andExpect(status().isNotFound());
  }

  @Test
  public void updatePerson_shouldReturnOk() throws Exception {
    when(personService.updateSimplePerson(any(), any())).thenReturn(new Person());
    mockMvc.perform(put("/persons/1")
            .contentType(MediaType.APPLICATION_JSON).content("{}"))
            .andExpect(status().isOk());
  }

  @Test
  public void updatePerson_shouldReturnUnprocessableEntity() throws Exception {
    when(personService.updateSimplePerson(any(), any())).thenThrow(NoSuchElementException.class);
    mockMvc.perform(put("/persons/1")
            .contentType(MediaType.APPLICATION_JSON).content("{}"))
            .andExpect(status().isUnprocessableEntity());
  }

  @Test
  public void getPersonsByFireStationNumber_shouldReturnOk() throws Exception {
    when(personService.getPersonsByFireStation(anyString())).thenReturn(new PersonByFireStationResponse());
    mockMvc.perform(get("/firestation")
            .contentType(MediaType.APPLICATION_JSON)
            .param("stationNumber", "1"))
            .andExpect(status().isOk());
  }

  @Test
  public void getChildrenByAddress_shouldReturnOk() throws Exception {
    when(personService.getChildrenByAddress(anyString())).thenReturn(new ChildrenByAddressResponse());
    mockMvc.perform(get("/childAlert")
            .contentType(MediaType.APPLICATION_JSON)
            .param("address", "1509 Culver St"))
            .andExpect(status().isOk());
  }

  @Test
  public void getPhonesByFireStationNumber_shouldReturnOk() throws Exception {
    when(personService.getPhonesByFireStation(anyString())).thenReturn(new ArrayList<>());
    mockMvc.perform(get("/phoneAlert")
            .contentType(MediaType.APPLICATION_JSON)
            .param("stationNumber", "1"))
            .andExpect(status().isOk());
  }

  @Test
  public void getPersonByAddress_shouldReturnOk() throws Exception {
    when(personService.getPersonsByAddress(anyString())).thenReturn(new PersonByAddressResponse());
    mockMvc.perform(get("/fire")
            .contentType(MediaType.APPLICATION_JSON)
            .param("address", "1509 Culver St"))
            .andExpect(status().isOk());
  }

  @Test
  public void getPersonInfo_shouldReturnOk() throws Exception {
    when(personService.getPersonInfo(anyString())).thenReturn(new ArrayList<>());
    mockMvc.perform(get("/personInfo")
            .contentType(MediaType.APPLICATION_JSON)
            .param("lastName", "Boyd"))
            .andExpect(status().isOk());
  }

  @Test
  public void getEmailsByCity_shouldReturnOk() throws Exception {
    when(personService.getEmailsByCity(anyString())).thenReturn(new ArrayList<>());
    mockMvc.perform(get("/communityEmail")
            .contentType(MediaType.APPLICATION_JSON)
            .param("city", "Culver"))
            .andExpect(status().isOk());
  }

}
