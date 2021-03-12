package com.openclassrooms.safetynet.repository;

import com.openclassrooms.safetynet.model.FireStation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FireStationRepository extends JpaRepository<FireStation, Long> {

}
