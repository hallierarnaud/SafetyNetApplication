package com.openclassrooms.safetynet.repository;

import com.openclassrooms.safetynet.model.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {

  Iterable<Person> findPersonByLastName(String lastName);

}
