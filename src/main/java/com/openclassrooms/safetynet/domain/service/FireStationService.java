package com.openclassrooms.safetynet.domain.service;

import com.openclassrooms.safetynet.controller.DTO.FireStationAddOrUpdateRequest;
import com.openclassrooms.safetynet.domain.object.FireStation;
import com.openclassrooms.safetynet.model.DAO.FireStationDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.persistence.EntityExistsException;

import lombok.Data;

@Data
@Service
public class FireStationService {

  @Autowired
  private FireStationDAO fireStationDAO;

  @Autowired
  MapService mapService;

  public List<FireStation> getFireStations() {
    return StreamSupport.stream(fireStationDAO.findAll().spliterator(), false)
            .collect(Collectors.toList());
  }

  public FireStation getFireStation(final Long id) {
    if (fireStationDAO.findById(id) == null) {
      throw new NoSuchElementException("firestation " + id + " doesn't exist");
    }
    return fireStationDAO.findById(id);
  }

  public FireStation addFireStation(FireStationAddOrUpdateRequest fireStationAddRequest) {
    if (fireStationDAO.existById(fireStationAddRequest.getId())) {
      throw new EntityExistsException("firestation " + fireStationAddRequest.getId() + " already exists");
    }
    FireStation fireStation = new FireStation();
    fireStation.setId(fireStationAddRequest.getId());
    mapService.updateFireStationWithFireStationRequest(fireStation, fireStationAddRequest);
    return fireStationDAO.addFireStation(fireStation);
  }

  public void deleteFireStation(final Long id) {
    if (!fireStationDAO.existById(id)) {
      throw new NoSuchElementException("firestation " + id +" doesn't exist");
    }
    fireStationDAO.deleteById(id);
  }

  public FireStation updateFireStation(Long id, FireStationAddOrUpdateRequest fireStationUpdateRequest) {
    if (fireStationDAO.findById(id) == null) {
      throw new NoSuchElementException("firestation " + id + " doesn't exist");
    }
    FireStation fireStation = fireStationDAO.findById(id);
    fireStation.setId(id);
    mapService.updateFireStationWithFireStationRequest(fireStation, fireStationUpdateRequest);
    return fireStationDAO.updateFireStation(id, fireStation);
  }

}
