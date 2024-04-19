package com.andres.curso.springboot.app.springbootcrud.controllers;

import com.andres.curso.springboot.app.springbootcrud.JWTGenerator;
import com.andres.curso.springboot.app.springbootcrud.entities.User;
import com.andres.curso.springboot.app.springbootcrud.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/demo")
@CrossOrigin
public class MainController {

    public static final String TOKENRANDOM = "tokenrandom";

    @Autowired
    private UserRepository userRepository;

    @GetMapping(path = "/all")
    public @ResponseBody ResponseEntity<?> getAllUsers(@RequestHeader("Token") String token) {
        if (JWTGenerator.verifyToken(token)) {
            return ResponseEntity.ok(userRepository.findAll());
        } else {
            return ResponseEntity.badRequest().body("Bad Token");
        }
    }

    @GetMapping(path = "/user/{id}")
    public @ResponseBody ResponseEntity<?> getUserById(
            @PathVariable Integer id,
            @RequestHeader("Token") String token) {
        if (JWTGenerator.verifyToken(token)) {
            return ResponseEntity.ok(userRepository.findById(id));
        }
        return ResponseEntity.badRequest().body("Bad Token");
    }

    @DeleteMapping(path = "/delete/{id}")
    public @ResponseBody ResponseEntity<?> deleteUser(
            @PathVariable Integer id,
            @RequestHeader("Token") String token) {
        if (JWTGenerator.verifyToken(token)) {
            userRepository.deleteById(id);
            return ResponseEntity.ok("User deleted");
        }
        return ResponseEntity.badRequest().body("Bad Token");
    }

    @PutMapping(path = "/put")
    public @ResponseBody ResponseEntity<?> updateUser(
            @RequestBody User user,
            @RequestHeader("Token") String token) {
        if (JWTGenerator.verifyToken(token)) {
            userRepository.save(user);
            return ResponseEntity.ok("User updated");
        }
        return ResponseEntity.badRequest().body("Bad Token");
    }

}
