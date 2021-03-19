package com.openclassrooms.safetynet.service;

import com.jsoniter.any.Any;
import com.openclassrooms.safetynet.model.MedicalRecord;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.repository.MedicalRecordRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
@Service
public class MedicalRecordDataImportation {

  @Autowired
  private MedicalRecordRepository medicalRecordRepository;

  private DataReader dataReader = new DataReader();

  public void getMedicalRecordList(String filePath, List<Person> personList) throws IOException {
    Any medicalAny = dataReader.getData(filePath).get("medicalrecords");
    List<MedicalRecord> medicalRecordList = new ArrayList<>();
    medicalAny.forEach(a -> {
      Person p = personList.stream()
              .filter(person -> { return person.getFirstName().equals(a.get("firstName").toString()) && person.getLastName().equals(a.get("lastName").toString()); })
              .findFirst().orElse(null);
      MedicalRecord medicalRecord = MedicalRecord.builder()
              .person(p)
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
