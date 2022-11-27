package com.cristovaoolegario.userdept.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.cristovaoolegario.userdept.entities.User;
import com.cristovaoolegario.userdept.repositories.UserRepository;

@RestController
@RequestMapping(value = "/users")
public class UserController {

  @Autowired
  private UserRepository repository;

  @GetMapping
  public List<User> findAll() {
    return repository.findAll();
  }

  @GetMapping(value = "/{id}")
  public User findById(@PathVariable Long id) {
    var result = repository.findById(id);
    if (result.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found");
    }
    return result.get();
  }

  @PostMapping
  public User insert(@RequestBody User user) {
    User result = repository.save(user);
    return result;
  }

}
