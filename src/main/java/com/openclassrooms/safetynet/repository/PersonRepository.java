package com.openclassrooms.safetynet.repository;

import com.openclassrooms.safetynet.model.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {

  List<Person> findByLastName(String lastName);

}
