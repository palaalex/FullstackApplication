package com.andres.curso.springboot.app.springbootcrud.repositories;

import com.andres.curso.springboot.app.springbootcrud.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}

