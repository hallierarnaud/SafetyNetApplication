package com.openclassrooms.safetynet.domain.service.DataImportation;

import com.jsoniter.any.Any;
import com.openclassrooms.safetynet.model.entity.FireStationEntity;
import com.openclassrooms.safetynet.model.repository.FireStationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import lombok.Data;

@Data
@Service
public class FireStationDataImportation {

  @Autowired
  private FireStationRepository fireStationRepository;

  private DataReader dataReader = new DataReader();

  public List<FireStationEntity> getFireStationList(String filePath) throws IOException {
    Any fireStationAny = dataReader.getData(filePath).get("firestations");
    Map<String, FireStationEntity> fireStationMap = new HashMap<>();
    fireStationAny.forEach(anyStation -> {
      fireStationMap.compute(anyStation.get("station").toString(),
              (k, v) -> v == null ?
                      new FireStationEntity(anyStation.get("station").toString()).addAddress(anyStation.get("address").toString()) :
                      v.addAddress(anyStation.get("address").toString()));
    });
    List<FireStationEntity> fireStationEntities = new ArrayList<>(fireStationMap.values());
    fireStationEntities.forEach(fireStation -> System.out.println("Firestation " + fireStation.toString()));
    return StreamSupport.stream(fireStationRepository.saveAll(fireStationEntities).spliterator(), false).collect(Collectors.toList());
  }
}
