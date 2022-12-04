package com.cristovaoolegario.sampleapi.api.services.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import com.cristovaoolegario.sampleapi.api.domain.User;
import com.cristovaoolegario.sampleapi.api.domain.dto.UserDTO;
import com.cristovaoolegario.sampleapi.api.repositories.UserRepository;
import com.cristovaoolegario.sampleapi.api.services.exceptions.ObjectNotFoundException;

@SpringBootTest
public class UserServiceImplementationTest {

  private static final Integer ID = 1;
  private static final String NAME = "test user";
  private static final String EMAIL = "test@gmail.com";
  private static final String PASSWORD = "123password";
  private static final String OBJECT_NOT_FOUND = "Object not found";
  private static final int INDEX = 0;

  @InjectMocks
  private UserServiceImplementation service;
  @Mock
  private UserRepository repository;
  @Mock
  private ModelMapper mapper;

  private User user;
  private UserDTO dto;
  private Optional<User> optionalUser;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    fillUsers();
  }

  private void fillUsers() {
    user = new User(ID, NAME, EMAIL, PASSWORD);
    dto = new UserDTO(ID, NAME, EMAIL, PASSWORD);
    optionalUser = Optional.of(new User(ID, NAME, EMAIL, PASSWORD));

  }

  @Test
  void GivenAValidIdWhenFindByIdThenReturnAnUserInstance() {
    Mockito.when(repository.findById(Mockito.anyInt())).thenReturn(optionalUser);

    User response = service.findById(ID);

    assertNotNull(response);
    assertEquals(User.class, response.getClass());
    assertEquals(ID, response.getId());
    assertEquals(NAME, response.getName());
    assertEquals(EMAIL, response.getEmail());
  }

  @Test
  void GivenAInvalidIdWhenFindByIdThenReturnAnObjectNotFoundException() {
    Mockito.when(repository.findById(Mockito.anyInt())).thenThrow(new ObjectNotFoundException(OBJECT_NOT_FOUND));
    try {
      service.findById(ID);
    } catch (Exception ex) {
      assertEquals(ObjectNotFoundException.class, ex.getClass());
      assertEquals(OBJECT_NOT_FOUND, ex.getMessage());
    }
  }

  @Test
  void GivenThereIsUsersRegisteredWhenFindAllThenReturnAListOfUsers() {
    Mockito.when(repository.findAll()).thenReturn(List.of(user));

    var response = service.findAll();

    assertNotNull(response);
    assertEquals(1, response.size());
    assertEquals(User.class, response.get(INDEX).getClass());

    assertEquals(ID, response.get(0).getId());
    assertEquals(NAME, response.get(0).getName());
    assertEquals(EMAIL, response.get(0).getEmail());
  }
}
