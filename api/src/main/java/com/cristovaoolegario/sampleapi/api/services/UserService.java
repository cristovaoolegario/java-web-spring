package com.cristovaoolegario.sampleapi.api.services;

import com.cristovaoolegario.sampleapi.api.domain.User;

public interface UserService {
  User findById(Integer id);
}
