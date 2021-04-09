package com.openclassrooms.safetynet.model.repository;

import com.openclassrooms.safetynet.model.entity.FireStationEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FireStationRepository extends JpaRepository<FireStationEntity, Long> {

}
