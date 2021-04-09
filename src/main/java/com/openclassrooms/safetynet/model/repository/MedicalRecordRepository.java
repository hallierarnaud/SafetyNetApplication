package com.openclassrooms.safetynet.model.repository;

import com.openclassrooms.safetynet.model.entity.MedicalRecordEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalRecordRepository extends CrudRepository<MedicalRecordEntity, Long> {

}
