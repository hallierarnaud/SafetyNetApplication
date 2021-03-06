package com.openclassrooms.safetynet.domain.service.DataImportation;

import com.jsoniter.any.Any;
import com.openclassrooms.safetynet.model.entity.FireStationEntity;
import com.openclassrooms.safetynet.model.entity.PersonEntity;
import com.openclassrooms.safetynet.model.repository.PersonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import lombok.Data;

@Data
@Service
public class PersonDataImportation {

  @Autowired
  private PersonRepository personRepository;

  private DataReader dataReader = new DataReader();

  public List<PersonEntity> getPersonList(String filePath, List<FireStationEntity> fireStationEntities) throws IOException {
    Any personAny = dataReader.getData(filePath).get("persons");
    List<PersonEntity> personEntities = new ArrayList<>();
    personAny.forEach(a -> {
      FireStationEntity f = fireStationEntities.stream()
              .filter(fireStation -> { return fireStation.getAddresses().contains(a.get("address").toString()); })
              .findFirst().orElse(null);
      personEntities.add(PersonEntity.builder()
            .firstName(a.get("firstName").toString())
            .address(a.get("address").toString())
            .city(a.get("city").toString())
            .lastName(a.get("lastName").toString())
            .phone(a.get("phone").toString())
            .zip(a.get("zip").toString())
            .email(a.get("email").toString())
            .fireStationEntity(f)
            .build());
    });
    return StreamSupport.stream(personRepository.saveAll(personEntities).spliterator(),false).collect(Collectors.toList());
  }

}
