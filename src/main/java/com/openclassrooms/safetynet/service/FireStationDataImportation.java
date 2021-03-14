package com.openclassrooms.safetynet.service;

import com.jsoniter.any.Any;
import com.openclassrooms.safetynet.model.FireStation;
import com.openclassrooms.safetynet.repository.FireStationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
@Service
public class FireStationDataImportation {

  @Autowired
  private FireStationRepository fireStationRepository;

  private DataReader dataReader = new DataReader();

  public void getFireStationList(String filePath) throws IOException {
    Any fireStationAny = dataReader.getData(filePath).get("firestations");
    Map<String, FireStation> fireStationMap = new HashMap<>();
    fireStationAny.forEach(anyStation -> {
      fireStationMap.compute(anyStation.get("station").toString(),
              (k, v) -> v == null ?
                      new FireStation(anyStation.get("station").toString()).addAddress(anyStation.get("address").toString()) :
                      v.addAddress(anyStation.get("address").toString()));
    });
    List<FireStation> fireStations = new ArrayList<>(fireStationMap.values());
    fireStations.forEach(firestation -> System.out.println("Firestation " + firestation.toString()));
    fireStationRepository.saveAll(fireStations);
  }
}
