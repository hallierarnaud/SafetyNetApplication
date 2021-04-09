package com.openclassrooms.safetynet.model.DAO;

import com.openclassrooms.safetynet.model.entity.PersonEntity;
import com.openclassrooms.safetynet.model.repository.PersonRepository;

import java.util.List;

public abstract class PersonDAO implements PersonRepository {

  @Override
  public List<PersonEntity> findByLastName(String lastName) {
    return null;
  }

}
