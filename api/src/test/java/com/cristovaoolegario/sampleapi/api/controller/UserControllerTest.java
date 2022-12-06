package com.cristovaoolegario.sampleapi.api.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cristovaoolegario.sampleapi.api.domain.User;
import com.cristovaoolegario.sampleapi.api.domain.dto.UserDTO;
import com.cristovaoolegario.sampleapi.api.services.exceptions.ObjectNotFoundException;
import com.cristovaoolegario.sampleapi.api.services.implementation.UserServiceImplementation;
import com.cristovaoolegario.sampleapi.api.utils.UserTestUtils;

import static com.cristovaoolegario.sampleapi.api.utils.UserTestUtils.EMAIL;
import static com.cristovaoolegario.sampleapi.api.utils.UserTestUtils.ID;
import static com.cristovaoolegario.sampleapi.api.utils.UserTestUtils.NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.cristovaoolegario.sampleapi.api.utils.UserTestUtils.OBJECT_NOT_FOUND;
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
    MockHttpServletRequest mockRequest = new MockHttpServletRequest();
    ServletRequestAttributes attributes = new ServletRequestAttributes(mockRequest);
    RequestContextHolder.setRequestAttributes(attributes);
  }

  @Test
  void GivenAValidIdWhenFindByIDThenReturnSucess() {
    Mockito.when(service.findById(anyInt())).thenReturn(user);
    Mockito.when(modelMapper.map(any(), any())).thenReturn(dto);

    var response = controller.findById(ID);

    assertNotNull(response);
    assertNotNull(response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());

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

  @Test
  void GivenValidInputWhenFindAllThenReturnAListOfUserDTO() {

    Mockito.when(service.findAll()).thenReturn(List.of(user));
    Mockito.when(modelMapper.map(any(), any())).thenReturn(dto);

    var response = controller.findAll();

    assertNotNull(response);
    assertNotNull(response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(ResponseEntity.class, response.getClass());
    assertEquals(ArrayList.class, response.getBody().getClass());
    assertEquals(UserDTO.class, response.getBody().get(INDEX).getClass());

    assertEquals(ID, response.getBody().get(INDEX).getId());
    assertEquals(NAME, response.getBody().get(INDEX).getName());
    assertEquals(EMAIL, response.getBody().get(INDEX).getEmail());
  }

  @Test
  void GivenAValidInputWhenCreateThenReturnCreatedUser() {
    Mockito.when(service.create(any())).thenReturn(user);

    var response = controller.create(dto);

    assertEquals(ResponseEntity.class, response.getClass());
    assertNotNull(response.getHeaders().get("Location"));
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
  }

  @Test
  void GivenAValidInputWhenUpdateUserThenReturnSucess() {
    Mockito.when(service.update(dto)).thenReturn(user);
    Mockito.when(modelMapper.map(any(), any())).thenReturn(dto);

    var response = controller.update(ID, dto);

    assertNotNull(response);
    assertNotNull(response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(ResponseEntity.class, response.getClass());
    assertEquals(UserDTO.class, response.getBody().getClass());

    assertEquals(ID, response.getBody().getId());
    assertEquals(NAME, response.getBody().getName());
    assertEquals(EMAIL, response.getBody().getEmail());
  }

  @Test
  void GivenAValidIdWhenDeleteUserThenReturnNoContent() {
    doNothing().when(service).Delete(anyInt());

    var response = controller.delete(ID);

    assertNotNull(response);
    assertEquals(ResponseEntity.class, response.getClass());
    assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

    verify(service, times(1)).Delete(anyInt());
  }
}
