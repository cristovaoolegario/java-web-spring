package com.cristovaoolegario.sampleapi.api.services.implementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;

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
import com.cristovaoolegario.sampleapi.api.services.exceptions.DataIntegrityViolationException;
import com.cristovaoolegario.sampleapi.api.services.exceptions.ObjectNotFoundException;

import com.cristovaoolegario.sampleapi.api.utils.UserTestUtils;
import static com.cristovaoolegario.sampleapi.api.utils.UserTestUtils.EMAIL;
import static com.cristovaoolegario.sampleapi.api.utils.UserTestUtils.ID;
import static com.cristovaoolegario.sampleapi.api.utils.UserTestUtils.NAME;
import static com.cristovaoolegario.sampleapi.api.utils.UserTestUtils.OBJECT_NOT_FOUND;
import static com.cristovaoolegario.sampleapi.api.utils.UserTestUtils.EMAIL_ALREADY_REGISTERED;
import static com.cristovaoolegario.sampleapi.api.utils.UserTestUtils.INDEX;

@SpringBootTest
public class UserServiceImplementationTest {

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
    user = UserTestUtils.NewTestUser();
    dto = UserTestUtils.NewUserDTO();
    optionalUser = UserTestUtils.NewOptionalUser();
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

  @Test
  void GivenAValidInputWhenCreateUserThenReturnCreatedUser() {
    Mockito.when(repository.save(any())).thenReturn(user);

    var response = service.create(dto);

    assertNotNull(response);
    assertEquals(User.class, response.getClass());

    assertEquals(ID, response.getId());
    assertEquals(NAME, response.getName());
    assertEquals(EMAIL, response.getEmail());
  }

  @Test
  void GivenAnInvalidInputWhenCreateUserThenReturnDataIntegrityViolationException() {
    Mockito.when(repository.findByEmail(anyString())).thenReturn(optionalUser);

    try {
      optionalUser.get().setId(2);
      service.create(dto);
    } catch (Exception ex) {
      assertEquals(DataIntegrityViolationException.class, ex.getClass());
      assertEquals(EMAIL_ALREADY_REGISTERED, ex.getMessage());
    }
  }

  @Test
  void GivenAValidInputWhenUpdateUserThenReturnUpdatedUser() {
    Mockito.when(repository.save(any())).thenReturn(user);

    var response = service.update(dto);

    assertNotNull(response);
    assertEquals(User.class, response.getClass());
    assertEquals(ID, response.getId());
    assertEquals(NAME, response.getName());
    assertEquals(EMAIL, response.getEmail());
  }

  @Test
  void GivenAnInalidInputWhenUpdateUserThenReturnDataIntegrityViolationException() {
    Mockito.when(repository.findByEmail(anyString())).thenReturn(optionalUser);

    try {
      optionalUser.get().setId(2);
      service.update(dto);
    } catch (Exception ex) {
      assertEquals(DataIntegrityViolationException.class, ex.getClass());
      assertEquals(EMAIL_ALREADY_REGISTERED, ex.getMessage());
    }
  }

  @Test
  void GivenAValidIdWhenDeleteUserThenDeleteWithSuccess() {
    Mockito.when(repository.findById(anyInt())).thenReturn(optionalUser);
    Mockito.doNothing().when(repository).deleteById(anyInt());

    service.Delete(ID);

    verify(repository, times(1)).deleteById(ID);
  }

  @Test
  void GivenAnInvalidIdWhenDeleteUserThenReturnAnObjectNotFoundException() {
    Mockito.when(repository.findById(anyInt())).thenThrow(new ObjectNotFoundException(OBJECT_NOT_FOUND));

    try {
      service.Delete(ID);
    } catch (Exception ex) {
      assertEquals(ObjectNotFoundException.class, ex.getClass());
      assertEquals(OBJECT_NOT_FOUND, ex.getMessage());
    }
  }
}
