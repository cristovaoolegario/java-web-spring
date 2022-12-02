package com.cristovaoolegario.sampleapi.api.services;

import java.util.List;

import com.cristovaoolegario.sampleapi.api.domain.User;
import com.cristovaoolegario.sampleapi.api.domain.dto.UserDTO;

public interface UserService {
  User findById(Integer id);

  List<User> findAll();

  User create(UserDTO user);

  User update(UserDTO user);
}
