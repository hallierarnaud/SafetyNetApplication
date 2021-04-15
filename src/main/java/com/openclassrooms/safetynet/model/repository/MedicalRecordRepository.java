package com.openclassrooms.safetynet.model.repository;

import com.openclassrooms.safetynet.model.entity.MedicalRecordEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface MedicalRecordRepository extends CrudRepository<MedicalRecordEntity, Long> {

  Boolean existsByPersonEntity_FirstNameAndPersonEntity_LastName(String personEntity_firstName, String personEntity_lastName);

  @Transactional
  void deleteByPersonEntity_FirstNameAndPersonEntity_LastName(String personEntity_firstName, String personEntity_lastName);

}
