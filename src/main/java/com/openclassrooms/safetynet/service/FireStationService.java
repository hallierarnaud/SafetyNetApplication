package com.openclassrooms.safetynet.service;

import com.openclassrooms.safetynet.model.FireStation;
import com.openclassrooms.safetynet.repository.FireStationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import lombok.Data;

@Data
@Service
public class FireStationService {

  @Autowired
  private FireStationRepository fireStationRepository;

  public Iterable<FireStation> getFireStations() {
    //Iterable<FireStation> fireStationList = fireStationRepository.findAll();
    return fireStationRepository.findAll();
  }

  public FireStation getFireStation(final Long id) {
    return fireStationRepository.findById(id).orElseThrow(() -> new NoSuchElementException("firestation " + id + " doesn't exist"));
  }

  public FireStation addFireStation(FireStation fireStation) {
    if (fireStationRepository.existsById(fireStation.getId())) {
      throw new EntityExistsException("firestation " + fireStation.getId() + " already exists");
    }
    return fireStationRepository.save(fireStation);
  }

  public void deleteFireStation(final Long id) {
    if (!fireStationRepository.existsById(id)) {
      throw new NoSuchElementException("firestation " + id + " doesn't exist");
    }
    fireStationRepository.deleteById(id);
  }

  public FireStation updateFireStation(Long id, FireStation fireStation) {
    if (!fireStationRepository.existsById(fireStation.getId())) {
      throw new EntityNotFoundException("firestation " + fireStation.getId() + " doesn't exist");
    }
    return fireStationRepository.save(fireStation);
  }

}
