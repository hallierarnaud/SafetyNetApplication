package com.openclassrooms.safetynet;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import com.openclassrooms.safetynet.model.FireStation;
import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.repository.FireStationRepository;
import com.openclassrooms.safetynet.repository.MedicalRecordRepository;
import com.openclassrooms.safetynet.repository.PersonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@RestController
public class SafetynetApplication implements ApplicationRunner {

  @Autowired
  private PersonRepository personRepository;

  @Autowired
  private FireStationRepository fireStationRepository;

  @Autowired
  private MedicalRecordRepository medicalRecordRepository;

  public static void main(String[] args) {
    SpringApplication.run(SafetynetApplication.class, args);
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {

    String filePath = "src/main/resources/data.json";
    byte[] bytesFile = Files.readAllBytes(new File(filePath).toPath());

    JsonIterator iter = JsonIterator.parse(bytesFile);
    Any any = iter.readAny();

    Any personAny = any.get("persons");
    List<Person> personList = new ArrayList<>();
    personAny.forEach(a -> personList.add(new Person.PersonBuilder()
            .firstName(a.get("firstName").toString())
            .address(a.get("address").toString())
            .city(a.get("city").toString())
            .lastName(a.get("lastName").toString())
            .phone(a.get("phone").toString())
            .zip(a.get("zip").toString())
            .email(a.get("email").toString())
            .build()));
    personRepository.saveAll(personList);

    Map<String, FireStation> fireStationMap = new HashMap<>();
    Any fireStationAny = any.get("firestations");
    fireStationAny.forEach(anyStation -> {
      fireStationMap.compute(anyStation.get("station").toString(),
              (k, v) -> v == null ?
                      new FireStation(anyStation.get("station").toString()).addAddress(anyStation.get("address").toString()) :
                      v.addAddress(anyStation.get("address").toString()));
    });
    List<FireStation> fireStations = new ArrayList<>(fireStationMap.values());
    fireStations.forEach(firestation -> System.out.println("Firestation " + firestation.toString()));
    fireStationRepository.saveAll(fireStations);

    Any medicalAny = any.get("medicalrecords");
    List<MedicalRecord> medicalRecordList = new ArrayList<>();
    medicalAny.forEach(a -> {
      MedicalRecord medicalRecord = MedicalRecord.builder()
              .firstName(a.get("firstName").toString())
              .lastName(a.get("lastName").toString())
              .birthdate(a.get("birthdate").toString())
              .build();
      List<String> allergyList = new ArrayList<>();
      a.get("allergies").forEach(b -> {
        allergyList.add(b.toString());
      });
      medicalRecord.setAllergies(allergyList);
      List<String> medicationList = new ArrayList<>();
      a.get("medications").forEach(c -> {
        medicationList.add(c.toString());
      });
      medicalRecord.setMedications(medicationList);
      medicalRecordList.add(medicalRecord);
    });
    medicalRecordRepository.saveAll(medicalRecordList);
  }

}
