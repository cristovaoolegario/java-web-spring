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
import com.cristovaoolegario.sampleapi.api.services.exceptions.DataIntegrityViolationException;
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
    findByEmail(user);
    return repository.save(mapper.map(user, User.class));
  }

  @Override
  public User update(UserDTO userDto) {
    findByEmail(userDto);
    return repository.save(mapper.map(userDto, User.class));
  }

  @Override
  public void Delete(Integer id) {
    findById(id);
    repository.deleteById(id);
  }

  private void findByEmail(UserDTO userDto) {
    Optional<User> user = repository.findByEmail(userDto.getEmail());
    if (user.isPresent() && !user.get().getId().equals(userDto.getId())) {
      throw new DataIntegrityViolationException("Email already registered!");
    }
  }
}
