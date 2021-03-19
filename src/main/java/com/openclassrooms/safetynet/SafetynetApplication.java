package com.openclassrooms.safetynet;

import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.service.DataReader;
import com.openclassrooms.safetynet.service.FireStationDataImportation;
import com.openclassrooms.safetynet.service.MedicalRecordDataImportation;
import com.openclassrooms.safetynet.service.PersonDataImportation;

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
    List<Person> personList = personDataImportation.getPersonList(INPUT_DATA_PATH);
    fireStationDataImportation.getFireStationList(INPUT_DATA_PATH);
    medicalRecordDataImportation.getMedicalRecordList(INPUT_DATA_PATH, personList);
  }

}
