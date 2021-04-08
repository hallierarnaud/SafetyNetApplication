package com.openclassrooms.safetynet.service;

import com.openclassrooms.safetynet.model.FireStation;
import com.openclassrooms.safetynet.repository.FireStationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import lombok.Data;

@Data
@Service
public class FireStationService {

  @Autowired
  private FireStationRepository fireStationRepository;

  public List<FireStation> getFireStations() {
    return StreamSupport.stream(fireStationRepository.findAll().spliterator(), false)
            .collect(Collectors.toList());
  }

  public FireStation getFireStation(final Long id) {
    return fireStationRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
  }

  public FireStation addFireStation(FireStation fireStation) {
    if (fireStationRepository.existsById(fireStation.getId())) {
      throw new EntityExistsException("firestation 1 already exists");
    }
    return fireStationRepository.save(fireStation);
  }

  public void deleteFireStation(final Long id) {
    if (!fireStationRepository.existsById(id)) {
      throw new NoSuchElementException("firestation 1 doesn't exist");
    }
    fireStationRepository.deleteById(id);
  }

  public FireStation updateFireStation(Long id, FireStation fireStation) {
    if (!fireStationRepository.existsById(fireStation.getId())) {
      throw new EntityNotFoundException("firestation 1 doesn't exist");
    }
    return fireStationRepository.save(fireStation);
  }

}
