package com.andres.curso.springboot.app.springbootcrud;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
