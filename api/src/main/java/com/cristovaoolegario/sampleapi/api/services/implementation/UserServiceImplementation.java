package com.cristovaoolegario.sampleapi.api.services.implementation;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cristovaoolegario.sampleapi.api.domain.User;
import com.cristovaoolegario.sampleapi.api.domain.dto.UserDTO;
import com.cristovaoolegario.sampleapi.api.repositories.UserRepository;
import com.cristovaoolegario.sampleapi.api.services.UserService;
import com.cristovaoolegario.sampleapi.api.services.exceptions.ObjectNotFoundException;

@Service
public class UserServiceImplementation implements UserService {

  @Autowired
  private UserRepository repository;

  @Autowired
  private ModelMapper mapper;

  @Override
  public User findById(Integer id) {
    Optional<User> obj = repository.findById(id);
    return obj.orElseThrow(() -> new ObjectNotFoundException("Object not found"));
  }

  @Override
  public List<User> findAll() {
    return repository.findAll();
  }

  @Override
  public User create(UserDTO user) {
    return repository.save(mapper.map(user, User.class));
  }

}
