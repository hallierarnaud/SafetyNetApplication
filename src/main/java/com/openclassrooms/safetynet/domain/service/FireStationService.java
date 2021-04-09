package com.openclassrooms.safetynet.domain.service;

import com.openclassrooms.safetynet.model.entity.FireStationEntity;
import com.openclassrooms.safetynet.model.repository.FireStationRepository;

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

  public List<FireStationEntity> getFireStations() {
    return StreamSupport.stream(fireStationRepository.findAll().spliterator(), false)
            .collect(Collectors.toList());
  }

  public FireStationEntity getFireStation(final Long id) {
    return fireStationRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
  }

  public FireStationEntity addFireStation(FireStationEntity fireStation) {
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

  public FireStationEntity updateFireStation(Long id, FireStationEntity fireStation) {
    if (!fireStationRepository.existsById(fireStation.getId())) {
      throw new EntityNotFoundException("firestation 1 doesn't exist");
    }
    return fireStationRepository.save(fireStation);
  }

}
