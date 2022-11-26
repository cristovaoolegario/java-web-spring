package com.cristovaoolegario.userdept.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
