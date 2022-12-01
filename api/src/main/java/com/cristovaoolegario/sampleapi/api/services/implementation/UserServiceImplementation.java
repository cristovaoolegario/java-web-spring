package com.cristovaoolegario.sampleapi.api.services.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cristovaoolegario.sampleapi.api.domain.User;
import com.cristovaoolegario.sampleapi.api.repositories.UserRepository;
import com.cristovaoolegario.sampleapi.api.services.UserService;
import com.cristovaoolegario.sampleapi.api.services.exceptions.ObjectNotFoundException;

@Service
public class UserServiceImplementation implements UserService {

  @Autowired
  private UserRepository repository;

  @Override
  public User findById(Integer id) {
    Optional<User> obj = repository.findById(id);
    return obj.orElseThrow(() -> new ObjectNotFoundException("Object not found"));
  }

  @Override
  public List<User> findAll() {
    return repository.findAll();
  }

}
