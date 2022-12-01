package com.cristovaoolegario.sampleapi.api.services;

import java.util.List;

import com.cristovaoolegario.sampleapi.api.domain.User;

public interface UserService {
  User findById(Integer id);

  List<User> findAll();
}
