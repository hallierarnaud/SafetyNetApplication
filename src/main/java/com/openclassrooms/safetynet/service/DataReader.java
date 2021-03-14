package com.openclassrooms.safetynet.service;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import lombok.Data;

@Data
@Service
public class DataReader {

  public Any getData(String filePath) throws IOException {
    byte[] bytesFile = Files.readAllBytes(new File(filePath).toPath());
    JsonIterator iter = JsonIterator.parse(bytesFile);
    Any any = iter.readAny();
    return any;
  }

}
