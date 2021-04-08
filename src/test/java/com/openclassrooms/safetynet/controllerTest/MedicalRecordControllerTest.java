package com.openclassrooms.safetynet.controllerTest;

import com.openclassrooms.safetynet.controller.MedicalRecordController;
import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.service.DataReader;
import com.openclassrooms.safetynet.service.FireStationDataImportation;
import com.openclassrooms.safetynet.service.MapService;
import com.openclassrooms.safetynet.service.MedicalRecordDataImportation;
import com.openclassrooms.safetynet.service.MedicalRecordService;
import com.openclassrooms.safetynet.service.PersonDataImportation;

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

@WebMvcTest(controllers = MedicalRecordController.class)
public class MedicalRecordControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private MedicalRecordService medicalRecordService;

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
    when(medicalRecordService.addMedicalRecord(any())).thenReturn(new MedicalRecord());
    mockMvc.perform(post("/medicalrecords")
            .contentType(MediaType.APPLICATION_JSON).content("{}"))
            .andExpect(status().isOk());
  }

  @Test
  public void postMedicalRecord_shouldReturnUnprocessableEntity() throws Exception {
    when(medicalRecordService.addMedicalRecord(any())).thenThrow(EntityExistsException.class);
    mockMvc.perform(post("/medicalrecords")
            .contentType(MediaType.APPLICATION_JSON).content("{}"))
            .andExpect(status().isUnprocessableEntity());
  }

  @Test
  public void deleteMedicalRecord_shouldReturnOk() throws Exception {
    doNothing().when(medicalRecordService).deleteMedicalRecord(any());
    mockMvc.perform(delete("/medicalrecords/1")).andExpect(status().isOk());
  }

  @Test
  public void deleteMedicalRecord_shouldReturnNotFound() throws Exception {
    doThrow(NoSuchElementException.class).when(medicalRecordService).deleteMedicalRecord(any());
    mockMvc.perform(delete("/medicalrecords/1")).andExpect(status().isNotFound());
  }

  @Test
  public void updateMedicalRecord_shouldReturnOk() throws Exception {
    when(medicalRecordService.updateMedicalRecord(any(), any())).thenReturn(new MedicalRecord());
    mockMvc.perform(put("/medicalrecords/1")
            .contentType(MediaType.APPLICATION_JSON).content("{}"))
            .andExpect(status().isOk());
  }

  @Test
  public void updateMedicalRecord_shouldReturnUnprocessableEntity() throws Exception {
    when(medicalRecordService.updateMedicalRecord(any(), any())).thenThrow(EntityNotFoundException.class);
    mockMvc.perform(put("/medicalrecords/1")
            .contentType(MediaType.APPLICATION_JSON).content("{}"))
            .andExpect(status().isUnprocessableEntity());
  }

}
