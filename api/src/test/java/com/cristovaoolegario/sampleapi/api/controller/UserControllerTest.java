package com.cristovaoolegario.sampleapi.api.controller;

import org.glassfish.jaxb.core.v2.model.core.ID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;

import com.cristovaoolegario.sampleapi.api.domain.User;
import com.cristovaoolegario.sampleapi.api.domain.dto.UserDTO;
import com.cristovaoolegario.sampleapi.api.services.exceptions.ObjectNotFoundException;
import com.cristovaoolegario.sampleapi.api.services.implementation.UserServiceImplementation;
import com.cristovaoolegario.sampleapi.api.utils.UserTestUtils;

import static com.cristovaoolegario.sampleapi.api.utils.UserTestUtils.EMAIL;
import static com.cristovaoolegario.sampleapi.api.utils.UserTestUtils.ID;
import static com.cristovaoolegario.sampleapi.api.utils.UserTestUtils.NAME;
import static com.cristovaoolegario.sampleapi.api.utils.UserTestUtils.PASSWORD;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

import java.util.Optional;

import static com.cristovaoolegario.sampleapi.api.utils.UserTestUtils.OBJECT_NOT_FOUND;
import static com.cristovaoolegario.sampleapi.api.utils.UserTestUtils.EMAIL_ALREADY_REGISTERED;
import static com.cristovaoolegario.sampleapi.api.utils.UserTestUtils.INDEX;

public class UserControllerTest {

  @InjectMocks
  private UserController controller;

  @Mock
  private UserServiceImplementation service;

  @Mock
  private ModelMapper modelMapper;

  private User user;
  private UserDTO dto;
  private Optional<User> optionalUser;

  @BeforeEach
  void setup() {
    MockitoAnnotations.openMocks(this);
    fillUsers();

  }

  private void fillUsers() {
    user = UserTestUtils.NewTestUser();
    dto = UserTestUtils.NewUserDTO();
    optionalUser = UserTestUtils.NewOptionalUser();
  }

  @Test
  void GivenAValidIdWhenFindByIDThenReturnSucess() {
    Mockito.when(service.findById(anyInt())).thenReturn(user);
    Mockito.when(modelMapper.map(any(), any())).thenReturn(dto);

    var response = controller.findById(ID);

    assertNotNull(response);
    assertNotNull(response.getBody());
    assertEquals(ResponseEntity.class, response.getClass());
    assertEquals(UserDTO.class, response.getBody().getClass());

    assertEquals(ID, response.getBody().getId());
    assertEquals(NAME, response.getBody().getName());
    assertEquals(EMAIL, response.getBody().getEmail());
  }

  @Test
  void GivenAnInvalidIdWhenFindByIDThenThrowObjectNotFoundException() {
    Mockito.when(service.findById(anyInt())).thenThrow(new ObjectNotFoundException(OBJECT_NOT_FOUND));

    try {
      controller.findById(ID);
    } catch (Exception ex) {
      assertEquals(ObjectNotFoundException.class, ex.getClass());
      assertEquals(OBJECT_NOT_FOUND, ex.getMessage());
    }
  }
}