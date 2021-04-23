package com.openclassrooms.safetynet.DAOTest;

import com.openclassrooms.safetynet.model.DAO.PersonDAO;
import com.openclassrooms.safetynet.model.entity.PersonEntity;
import com.openclassrooms.safetynet.model.repository.PersonRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PersonDAOTest {

  @Mock
  private PersonRepository personRepository;

  @InjectMocks
  private PersonDAO personDAO;

  @Test
  public void getPersonsByFireStationNumber_shouldReturnOk () {
    // GIVEN
    PersonEntity personEntity = new PersonEntity();
    personEntity.setId(1L);
    personEntity.setFirstName("Homer");
    List<PersonEntity> personEntityByFireStationList = new ArrayList<>();
    personEntityByFireStationList.add(personEntity);
    when(personRepository.findAllByFireStationEntityStationNumber(anyString())).thenReturn(personEntityByFireStationList);

    // WHEN
    String expectedFirstName = personDAO.getPersonsByFireStationNumber(anyString()).get(0).getFirstName();

    // THEN
    assertEquals("Homer", expectedFirstName);
    verify(personRepository).findAllByFireStationEntityStationNumber(anyString());
  }

}
