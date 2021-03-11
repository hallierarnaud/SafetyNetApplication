package com.openclassrooms.safetynet;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import com.openclassrooms.safetynet.model.FireStation;
import com.openclassrooms.safetynet.model.Person;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootApplication
@RestController
public class SafetynetApplication {

  public static void main(String[] args) throws IOException {
    SpringApplication.run(SafetynetApplication.class, args);

    String filePath = "src/main/resources/data.json";
    byte[] bytesFile = Files.readAllBytes(new File(filePath).toPath());

    JsonIterator iter = JsonIterator.parse(bytesFile);
    Any any = iter.readAny();

    Any personAny = any.get("persons");
    List<Person> persons = new ArrayList<>();
    personAny.forEach(a -> persons.add(new Person.PersonBuilder()
            .firstName(a.get("firstName").toString())
            .address(a.get("address").toString())
            .city(a.get("city").toString())
            .lastName(a.get("lastName").toString())
            .phone(a.get("phone").toString())
            .zip(a.get("zip").toString())
            .email(a.get("email").toString())
            .build()));

    //persons.forEach(p -> System.out.println(p.firstName.concat(p.lastName).concat(p.address).concat(p.city).concat(p.phone).concat(p.zip)));

    Map<String, FireStation> fireStationMap = new HashMap<>();
    Any fireStationAny = any.get("firestations");
    fireStationAny.forEach(anyStation -> {
      fireStationMap.compute(anyStation.get("station").toString(),
              (k, v) -> v == null ?
                      new FireStation(anyStation.get("station").toString()).addAddress(anyStation.get("address").toString()) :
                      v.addAddress(anyStation.get("address").toString()));
    });

    List<FireStation> fireStations = fireStationMap.values().stream().collect(Collectors.toList());
    fireStations.forEach(firestation -> System.out.println("Firestation " + firestation.toString()));

    for(FireStation fireStation : fireStations) {
      if(fireStation.getAddresses().contains("489 Manchester St")) {
        System.out.println("Firestation " + fireStation.getStationNumber() + " selected");
        break;
      }
    }

    Any medicalAny = any.get("medicalrecords");
    medicalAny.forEach(medicalRecord -> {System.out.println(medicalRecord.get("firstName").toString().concat(medicalRecord.get("lastName").toString())
            .concat(medicalRecord.get("birthdate").toString()));
      Any medications = medicalRecord.get("medications");
      medications.forEach(a -> System.out.println(a.toString()));

      Any allergies = medicalRecord.get("allergies");
      allergies.forEach(a -> System.out.println(a.toString()));
    });
  }

}
