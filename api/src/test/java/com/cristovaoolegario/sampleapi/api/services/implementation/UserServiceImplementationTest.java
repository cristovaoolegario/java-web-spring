package com.cristovaoolegario.sampleapi.api.services.implementation;

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

@SpringBootTest
public class UserServiceImplementationTest {

  private static final Integer ID = 1;
  private static final String NAME = "test user";
  private static final String EMAIL = "test@gmail.com";
  private static final String PASSWORD = "123password";

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

    Assertions.assertNotNull(response);
    Assertions.assertEquals(User.class, response.getClass());
    Assertions.assertEquals(ID, response.getId());
    Assertions.assertEquals(NAME, response.getName());
    Assertions.assertEquals(EMAIL, response.getEmail());

  }
}
