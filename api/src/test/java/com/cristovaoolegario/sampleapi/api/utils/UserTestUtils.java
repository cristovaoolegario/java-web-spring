package com.cristovaoolegario.sampleapi.api.utils;

import java.util.Optional;

import com.cristovaoolegario.sampleapi.api.domain.User;
import com.cristovaoolegario.sampleapi.api.domain.dto.UserDTO;

public class UserTestUtils {

  public static final Integer ID = 1;
  public static final String NAME = "test user";
  public static final String EMAIL = "test@gmail.com";
  public static final String PASSWORD = "123password";
  public static final String OBJECT_NOT_FOUND = "Object not found";
  public static final String EMAIL_ALREADY_REGISTERED = "Email already registered!";
  public static final int INDEX = 0;

  public static User NewTestUser() {
    return new User(ID, NAME, EMAIL, PASSWORD);
  }

  public static UserDTO NewUserDTO() {
    return new UserDTO(ID, NAME, EMAIL, PASSWORD);
  }

  public static Optional<User> NewOptionalUser() {
    return Optional.of(new User(ID, NAME, EMAIL, PASSWORD));
  }
}
