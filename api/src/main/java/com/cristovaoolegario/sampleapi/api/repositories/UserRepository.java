package com.cristovaoolegario.sampleapi.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cristovaoolegario.sampleapi.api.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
