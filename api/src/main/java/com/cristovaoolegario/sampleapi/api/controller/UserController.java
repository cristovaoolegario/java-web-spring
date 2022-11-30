package com.cristovaoolegario.sampleapi.api.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cristovaoolegario.sampleapi.api.domain.dto.UserDTO;
import com.cristovaoolegario.sampleapi.api.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserController {

  @Autowired
  private ModelMapper mapper;

  @Autowired
  private UserService service;

  @GetMapping(value = "/{id}")
  public ResponseEntity<UserDTO> findById(@PathVariable Integer id) {
    return ResponseEntity.ok().body(mapper.map(service.findById(id), UserDTO.class));
  }

}
