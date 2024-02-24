package com.andres.curso.springboot.app.springbootcrud.services;

import com.andres.curso.springboot.app.springbootcrud.entities.User;

import java.util.List;

public interface UserServices {
    List<User> findAll();

    User save(User user);
}
