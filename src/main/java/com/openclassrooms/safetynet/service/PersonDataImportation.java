package com.openclassrooms.safetynet.service;

import com.jsoniter.any.Any;
import com.openclassrooms.safetynet.model.Person;
import com.openclassrooms.safetynet.repository.PersonRepository;

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

  public List<Person> getPersonList(String filePath) throws IOException {
    Any personAny = dataReader.getData(filePath).get("persons");
    List<Person> personList = new ArrayList<>();
    personAny.forEach(a -> personList.add(Person.builder()
            .firstName(a.get("firstName").toString())
            .address(a.get("address").toString())
            .city(a.get("city").toString())
            .lastName(a.get("lastName").toString())
            .phone(a.get("phone").toString())
            .zip(a.get("zip").toString())
            .email(a.get("email").toString())
            .build()));
    return StreamSupport.stream(personRepository.saveAll(personList).spliterator(),false).collect(Collectors.toList());
  }

}
