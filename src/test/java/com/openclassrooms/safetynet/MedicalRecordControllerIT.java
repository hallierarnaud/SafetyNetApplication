package com.openclassrooms.safetynet;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MedicalRecordControllerIT {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void testGetMedicalRecords() throws Exception {

    mockMvc.perform(get("/medicalrecords"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id", is(1)));

  }

}
