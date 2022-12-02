package com.cristovaoolegario.sampleapi.api.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cristovaoolegario.sampleapi.api.domain.User;
import com.cristovaoolegario.sampleapi.api.domain.dto.UserDTO;
import com.cristovaoolegario.sampleapi.api.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserController {

  private static final String ID = "/{id}";

  @Autowired
  private ModelMapper mapper;

  @Autowired
  private UserService service;

  @GetMapping(value = ID)
  public ResponseEntity<UserDTO> findById(@PathVariable Integer id) {
    return ResponseEntity.ok().body(mapper.map(service.findById(id), UserDTO.class));
  }

  @GetMapping
  public ResponseEntity<List<UserDTO>> findAll() {
    List<UserDTO> dtoList = service.findAll().stream().map(item -> mapper.map(item, UserDTO.class))
        .collect(Collectors.toList());
    return ResponseEntity.ok().body(dtoList);

  }

  @PostMapping
  public ResponseEntity<UserDTO> create(@RequestBody UserDTO obj) {
    User newUser = service.create(obj);
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path(ID).buildAndExpand(newUser.getId()).toUri();
    return ResponseEntity.created(uri).build();
  }

  @PutMapping(value = ID)
  public ResponseEntity<UserDTO> update(@PathVariable Integer id, @RequestBody UserDTO obj) {
    obj.setId(id);
    User newUser = service.update(obj);
    return ResponseEntity.ok().body(mapper.map(newUser, UserDTO.class));
  }

  @DeleteMapping(value = ID)
  public ResponseEntity<UserDTO> delete(@PathVariable Integer id) {
    service.Delete(id);
    return ResponseEntity.noContent().build();
  }
}
