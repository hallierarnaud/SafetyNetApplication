package com.openclassrooms.safetynet.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonId implements Serializable {

  private String firstName;

  private String lastName;

}
