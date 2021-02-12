package com.openclassrooms.safetynet.service;

import com.openclassrooms.safetynet.model.FireStation;
import com.openclassrooms.safetynet.repository.FireStationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

import lombok.Data;

@Data
@Service
public class FireStationService {

  @Autowired
  private FireStationRepository fireStationRepository;

  public FireStation getFireStation(final Long id) {
    return fireStationRepository.findById(id).orElseThrow(() -> new NoSuchElementException("la caserne " + id + " n'existe pas"));
  }

  public Iterable<FireStation> getFireStations() {
    return fireStationRepository.findAll();
  }

  public void deleteFireStation(final Long id) {
    if (fireStationRepository.existsById(id)) {
      fireStationRepository.deleteById(id);
    }
  }

  public FireStation saveFireStation(FireStation fireStation) {
    FireStation savedFireStation = fireStationRepository.save(fireStation);
    return savedFireStation;
  }

}
