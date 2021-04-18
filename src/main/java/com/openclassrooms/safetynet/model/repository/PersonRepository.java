package com.openclassrooms.safetynet.model.repository;

import com.openclassrooms.safetynet.model.entity.PersonEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PersonRepository extends CrudRepository<PersonEntity, Long> {

  Boolean existsByFirstNameAndLastName(String firstName, String lastName);

  @Transactional
  void deleteByFirstNameAndLastName(String firstName, String lastName);

  List<PersonEntity> findAllByFireStationEntityStationNumber(String stationNumber);

  List<PersonEntity> findAllByAddressLike(String address);

}
