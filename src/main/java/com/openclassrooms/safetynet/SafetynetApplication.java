package com.openclassrooms.safetynet;

import com.openclassrooms.safetynet.domain.service.DataImportation.DataReader;
import com.openclassrooms.safetynet.domain.service.DataImportation.FireStationDataImportation;
import com.openclassrooms.safetynet.domain.service.DataImportation.MedicalRecordDataImportation;
import com.openclassrooms.safetynet.domain.service.DataImportation.PersonDataImportation;
import com.openclassrooms.safetynet.model.entity.FireStationEntity;
import com.openclassrooms.safetynet.model.entity.PersonEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@SpringBootApplication
@RestController
public class SafetynetApplication implements ApplicationRunner {

  @Autowired
  private DataReader dataReader;

  @Autowired
  private PersonDataImportation personDataImportation;

  @Autowired
  private FireStationDataImportation fireStationDataImportation;

  @Autowired
  private MedicalRecordDataImportation medicalRecordDataImportation;

  public static final String INPUT_DATA_PATH = "src/main/resources/data.json";

  public static void main(String[] args) {
    SpringApplication.run(SafetynetApplication.class, args);
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    //dataReader.getData(INPUT_DATA_PATH);
    List<FireStationEntity> fireStationList = fireStationDataImportation.getFireStationList(INPUT_DATA_PATH);
    List<PersonEntity> personList = personDataImportation.getPersonList(INPUT_DATA_PATH, fireStationList);
    medicalRecordDataImportation.getMedicalRecordList(INPUT_DATA_PATH, personList);
  }

}
