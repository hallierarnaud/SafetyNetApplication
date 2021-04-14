package com.openclassrooms.safetynet.model.DAO;

import com.openclassrooms.safetynet.domain.object.FireStation;
import com.openclassrooms.safetynet.model.entity.FireStationEntity;
import com.openclassrooms.safetynet.model.repository.FireStationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public class FireStationDAO {

  @Autowired
  private FireStationRepository fireStationRepository;

  @Autowired
  MapDAO mapDAO;

  public FireStation findById(Long id) {
    FireStationEntity fireStationEntity = fireStationRepository.findById(id).orElseThrow(() -> new NoSuchElementException("firestation " + id + " doesn't exist"));
    FireStation fireStation = new FireStation();
    mapDAO.updateFireStationWithFireStationEntity(fireStation, fireStationEntity);
    return fireStation;
  }

  public List<FireStation> findAll() {
    List<FireStationEntity> fireStationEntities =  StreamSupport.stream(fireStationRepository.findAll().spliterator(),false)
            .collect(Collectors.toList());
    return fireStationEntities.stream().map((fireStationEntity) -> {
      FireStation fireStation = new FireStation();
      mapDAO.updateFireStationWithFireStationEntity(fireStation, fireStationEntity);
      return fireStation;
    }).collect(Collectors.toList());
  }

  public FireStation updateFireStation(Long id, FireStation fireStation) {
    FireStationEntity fireStationEntity = fireStationRepository.findById(id).orElseThrow(() -> new NoSuchElementException("firestation " + id + " doesn't exist"));
    mapDAO.updateFireStationEntityWithFireStation(fireStationEntity, fireStation);
    fireStationRepository.save(fireStationEntity);
    return fireStation;
  }

  public FireStation addFireStation(FireStation fireStation) {
    FireStationEntity fireStationEntity = new FireStationEntity();
    mapDAO.updateFireStationEntityWithFireStation(fireStationEntity, fireStation);
    fireStationRepository.save(fireStationEntity);
    return fireStation;
  }

  public Boolean existById(Long id) {
    return fireStationRepository.existsById(id);
  }

  public void deleteById(Long id) {
    fireStationRepository.deleteById(id);
  }

}
