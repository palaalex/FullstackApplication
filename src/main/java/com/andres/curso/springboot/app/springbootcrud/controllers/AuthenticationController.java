package com.andres.curso.springboot.app.springbootcrud.controllers;

import com.andres.curso.springboot.app.springbootcrud.JWTGenerator;
import com.andres.curso.springboot.app.springbootcrud.entities.AuthClass;
import com.andres.curso.springboot.app.springbootcrud.entities.User;
import com.andres.curso.springboot.app.springbootcrud.repositories.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.andres.curso.springboot.app.springbootcrud.entities.Token;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "/api")
public class AuthenticationController {

    @Autowired
    UserRepository userRepository;
    @Qualifier("conversionService")

    @PostMapping(path = "/register")
    public @ResponseBody ResponseEntity<?> register(@RequestBody User user) {
        userRepository.save(user);
        String token = JWTGenerator.generateFromUser(user);
        return ResponseEntity.ok(token);
    }

    @PostMapping(path = "/login")
    public @ResponseBody ResponseEntity<?> getToken(@RequestBody User user) {
        try{
            User newUser = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
            if(newUser != null) {
                Token token = new Token();
                token.setToken(JWTGenerator.generateFromUser(newUser));
                return ResponseEntity.ok(token);
            }
            return ResponseEntity.badRequest().build();
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping(path = "/verify")
    public @ResponseBody ResponseEntity<?> verify(@RequestHeader String token) {
        Boolean isVerified = JWTGenerator.verifyToken(token);
        if (isVerified) {
            return ResponseEntity.ok("Token verified");
        }
        return ResponseEntity.badRequest().body("Token verification failed");
    }
}
