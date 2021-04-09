package com.openclassrooms.safetynet.model.repository;

import com.openclassrooms.safetynet.model.entity.PersonEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends CrudRepository<PersonEntity, Long> {

  List<PersonEntity> findByLastName(String lastName);

}
