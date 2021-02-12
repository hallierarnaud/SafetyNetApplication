package com.openclassrooms.safetynet;

import com.openclassrooms.safetynet.controller.PersonController;
import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.service.MedicalRecordService;

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

@WebMvcTest(controllers = PersonController.class)
public class MedicalRecordControllerTest {


  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private MedicalRecordService medicalRecordService;

  @Test
  public void getMedicalRecords_shouldReturnOk() throws Exception {
    when(medicalRecordService.getMedicalRecord(any())).thenReturn(new MedicalRecord());
    mockMvc.perform(get("/medicalrecords")).andExpect(status().isOk());
  }

  @Test
  public void getMedicalRecordById_shouldReturnOk() throws Exception {
    when(medicalRecordService.getMedicalRecord(any())).thenReturn(new MedicalRecord());
    mockMvc.perform(get("/medicalrecords/1")).andExpect(status().isOk());
  }

  @Test
  public void getMedicalRecordById_shouldReturnNotFound() throws Exception {
    when(medicalRecordService.getMedicalRecord(any())).thenThrow(NoSuchElementException.class);
    mockMvc.perform(get("/medicalrecords/1")).andExpect(status().isNotFound());
  }

  @Test
  public void postMedicalRecord_shouldReturnOk() throws Exception {
    mockMvc.perform(post("/medicalrecords")
            .param("id", "1")
            .param("firstName", "Bart")
            .param("lastName", "Simpson")
            .param("birthdate", "01/01/2000")
            .param("medications", "truc")
            .param("allergies", "machin"))
            .andExpect(status().isOk());
  }

  @Test
  public void deleteMedicalRecord_shouldReturnOk() throws Exception {
    when(medicalRecordService.getMedicalRecord(any())).thenReturn(new MedicalRecord());
    mockMvc.perform(delete("/medicalrecords/1")).andExpect(status().isOk());
  }

  @Test
  public void updateMedicalRecord_shouldReturnOk() throws Exception {
    when(medicalRecordService.getMedicalRecord(any())).thenReturn(new MedicalRecord());
    mockMvc.perform(put("/medicalrecords/1")
            .param("id", "1")
            .param("firstName", "Bart")
            .param("lastName", "Simpson")
            .param("birthdate", "01/01/2000")
            .param("medications", "truc")
            .param("allergies", "machin"))
            .andExpect(status().isOk());
  }

}
